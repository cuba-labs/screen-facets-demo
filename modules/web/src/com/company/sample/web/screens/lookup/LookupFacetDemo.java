package com.company.sample.web.screens.lookup;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.app.security.user.browse.UserBrowser;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.entity.User;

import javax.inject.Inject;
import java.util.Collection;

@SuppressWarnings({"unused", "InvalidInstalledDelegate"})
@LoadDataBeforeShow
@UiController("sample_LookupFacetDemo")
@UiDescriptor("lookup-facet-demo.xml")
public class LookupFacetDemo extends Screen {

    @Inject
    private Notifications notifications;
    @Inject
    private Metadata metadata;

    @Inject
    private CollectionContainer<User> userDc;

    @Inject
    private PickerField<User> pickerField;
    @Inject
    private Table<User> usersTable;

    @Inject
    private Action lookupAction;

    @Inject
    private LookupScreenFacet<User, UserBrowser> fieldLookup;

    @Subscribe
    public void onInit(InitEvent event) {
        User testUser = metadata.create(User.class);
        testUser.setName("Test user");

        pickerField.setValue(testUser);
    }

    @Install(to = "userLookup", subject = "selectHandler")
    public void handleLookupSelect(Collection<User> selected) {
        notifications.create(Notifications.NotificationType.HUMANIZED)
                .withCaption("Count of selected users:")
                .withDescription(String.valueOf(selected.size()))
                .show();
    }

    @Install(to = "tableLookup", subject = "selectValidator")
    public boolean userSelectPredicate(LookupScreen.ValidationContext<User> ctx) {
        return true;
    }

    @Install(to = "fieldLookup", subject = "transformation")
    public Collection<User> transformSelection(Collection<User> selected) {
        selected.forEach(user -> user.setName("Transformed!"));
        return selected;
    }

    @Subscribe("pfLookupBtn")
    public void onPfLookupBtnClick(Button.ClickEvent event) {
        fieldLookup.show();
    }
}