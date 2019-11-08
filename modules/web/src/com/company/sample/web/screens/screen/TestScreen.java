package com.company.sample.web.screens.screen;

import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import javax.inject.Inject;

@UiController("sample_TestScreen")
@UiDescriptor("test-screen.xml")
public class TestScreen extends Screen {

    @Inject
    private Notifications notifications;

    private int num;

    public void setNum(int num) {
        this.num = num;
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption("Number passed:")
                .withDescription(String.valueOf(num))
                .show();
    }
}