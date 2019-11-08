package com.company.sample.web.screens.editor;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.ScreenFacet;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.HasLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.entity.Role;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.global.UserSession;

import javax.inject.Inject;

@SuppressWarnings({"unused", "InvalidInstalledDelegate"})
@LoadDataBeforeShow
@UiController("sample_EditorFacetDemo")
@UiDescriptor("editor-facet-demo.xml")
public class EditorFacetDemo extends Screen {

    @Inject
    private Metadata metadata;
    @Inject
    private UserSession userSession;

    @Inject
    private CollectionContainer<User> userDc;

    @Install(to = "userEditor", subject = "entityProvider")
    protected User createUser() {
        User user = metadata.create(User.class);
        user.setFirstName("Created in Entity Provider");
        return user;
    }

    @Install(to = "userEditor", subject = "initializer")
    protected void initUser(User user) {
        user.setLastName("Initialized in Initializer");
    }

    @Install(to = "roleEditor", subject = "entityProvider")
    protected Role provideRole() {
        return userSession.getCurrentOrSubstitutedUser()
                .getUserRoles().get(0)
                .getRole();
    }

    @Subscribe("userEditor")
    protected void onEditorClose(ScreenFacet.AfterCloseEvent event) {
        ((HasLoader) userDc).getLoader().load();
    }
}