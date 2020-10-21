package com.aranda.pruebatest;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.accessibility.AccessibilityWindowInfo;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MainActivityTest {

    public static final String BASIC_SAMPLE_PACKAGE = "com.aranda.pruebatest";
    public static final int LAUNCH_TIMEOUT = 5000;
    public UiDevice device;

    @Before
    public void testStartMainActivityFromHomeScreen() {

        // Initialize UiDevice instance
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        device.pressHome();

        // Wait for launcher
        final String launcherPackage = device.getLauncherPackageName();
        MatcherAssert.assertThat(launcherPackage, IsNull.notNullValue());
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                LAUNCH_TIMEOUT);

        // Launch the app
        Context context = ApplicationProvider.getApplicationContext();
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        // Wait for the app to appear
        device.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)), LAUNCH_TIMEOUT);

    }

    @Test
    public void checkNombre() throws UiObjectNotFoundException {
        UiObject txtNombre = device.findObject(new UiSelector().resourceId("com.aranda.pruebatest:id/txtName"));
        Assert.assertTrue(txtNombre.exists());

        txtNombre.setText("Juanita");
    }

    @Test
    public void checkApellido() throws UiObjectNotFoundException {
        UiObject txtApellido = device.findObject(new UiSelector().resourceId("com.aranda.pruebatest:id/txtApel"));
        Assert.assertTrue(txtApellido.exists());

        txtApellido.setText("Perez");
    }

    @Test
    public void checkCorreElectronico() throws UiObjectNotFoundException {
        UiObject txtControl = device.findObject(new UiSelector().resourceId("com.aranda.pruebatest:id/txtEmail"));
        Assert.assertTrue(txtControl.exists());
        if (isKeyboardOpened()) {
            device.pressBack();
        }
        txtControl.setText("juanita.perez@arandasoft.com");
    }

    @Test
    public void checkGenero() throws UiObjectNotFoundException {
        if (isKeyboardOpened()) {
            device.pressBack();
        }
        UiObject cmbGenero = device.findObject(new UiSelector().resourceId("com.aranda.pruebatest:id/txtGenero"));
        Assert.assertTrue(cmbGenero.exists());
        cmbGenero.click();

        UiObject opcionFemenino = device.findObject(new UiSelector().index(1));
        Assert.assertTrue(opcionFemenino.exists());
        opcionFemenino.click();

        if (isKeyboardOpened()) {
            device.pressBack();
        }

        /*UiObject opcionMasculino = device.findObject(new UiSelector().index(2));
        Assert.assertTrue(opcionMasculino.exists());
        opcionMasculino.click();*/
    }

    @Test
    public void checkFormulario() throws UiObjectNotFoundException {
        checkNombre();
        checkApellido();
        checkCorreElectronico();
        checkGenero();
    }

/*

    @Test
    public void checkForm() throws UiObjectNotFoundException {
        Context context = ApplicationProvider.getApplicationContext();

        UiObject txtName = device.findObject(new UiSelector().resourceId("com.aranda.pruebatest:id/txtName"));
        Assert.assertTrue(txtName.exists());
        txtName.setText("Maria");

        if (isKeyboardOpened()) {
            device.pressBack();
        }

        UiObject txtApel = device.findObject(new UiSelector().resourceId("com.aranda.pruebatest:id/txtApel"));
        Assert.assertTrue(txtApel.exists());
        txtApel.setText("Perez");

        if (isKeyboardOpened()) {
            device.pressBack();
        }
        UiObject txtDate = device.findObject(new UiSelector().resourceId("com.aranda.pruebatest:id/txtDate"));
        Assert.assertTrue(txtDate.exists());
        txtDate.click();

        if (isKeyboardOpened()) {
            device.pressBack();
        }
        if (Build.VERSION.SDK_INT >= 25) {
            UiObject day = device.findObject(new UiSelector().text("11"));
            Assert.assertTrue(day.exists());
            day.click();
        } else {
            UiObject day = device.findObject(new UiSelector().resourceId("android:id/numberpicker_input"));
            Assert.assertTrue(day.exists());
            day.swipeDown(6);
        }


        UiObject btnDone = device.findObject(new UiSelector().resourceId("android:id/button1"));
        Assert.assertTrue(btnDone.exists());
        btnDone.click();

        UiObject txtEmail = device.findObject(new UiSelector().resourceId("com.aranda.pruebatest:id/txtEmail"));
        Assert.assertTrue(txtEmail.exists());
        txtEmail.setText("maria.perez@gmail.com");

        if (isKeyboardOpened()) {
            device.pressBack();
        }

        UiObject txtGenero = device.findObject(new UiSelector().resourceId("com.aranda.pruebatest:id/txtGenero"));
        Assert.assertTrue(txtGenero.exists());
        txtGenero.click();

        UiObject txtGeneroFemenino = device.findObject(new UiSelector().resourceId("android:id/text1"));
        Assert.assertTrue(txtGeneroFemenino.exists());
        txtGeneroFemenino.click();

        UiScrollable scrollView = new UiScrollable(new UiSelector().scrollable(true).resourceId("com.aranda.pruebatest:id/scrollView"));
        if (scrollView.exists()) {
            scrollView.getChildByText(new UiSelector().className(android.widget.Button.class.getName()), "GUARDAR").click();
        } else {
            UiObject btnSave = device.findObject(new UiSelector().resourceId("com.aranda.pruebatest:id/btnSave"));
            Assert.assertTrue(btnSave.exists());
            btnSave.click();
        }

        UiObject txtSaveConfirm = device.findObject(new UiSelector().resourceId("android:id/button1"));
        Assert.assertTrue(txtSaveConfirm.exists());
        txtSaveConfirm.click();


    }*/

    boolean isKeyboardOpened() {
        for (AccessibilityWindowInfo window : InstrumentationRegistry.getInstrumentation().getUiAutomation().getWindows()) {
            if (window.getType() == AccessibilityWindowInfo.TYPE_INPUT_METHOD) {
                return true;
            }
        }
        return false;
    }
}
