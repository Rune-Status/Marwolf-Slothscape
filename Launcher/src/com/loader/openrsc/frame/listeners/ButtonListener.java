package com.loader.openrsc.frame.listeners;

import com.loader.openrsc.Constants;
import com.loader.openrsc.frame.AppFrame;
import com.loader.openrsc.util.ClientLauncher;
import com.loader.openrsc.util.Utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

public class ButtonListener implements ActionListener {
    @Override
    public void actionPerformed(final ActionEvent event) {
        final String action = event.getActionCommand().toLowerCase();
        switch (action) {
            case "forum": {
                Utils.openWebpage(Constants.BASE_URL + "/elite/board");
                return;
            }
            case "bug reports": {
                Utils.openWebpage("https://goo.gl/forms/nnhSln7S81l4I26t2");
                return;
            }
            case "report bots": {
                Utils.openWebpage("https://goo.gl/forms/AkBzpOzgAmzWiZ8H2");
                return;
            }
            case "discord": {
                Utils.openWebpage("https://discord.gg/atX3Ruy");
                return;
            }
            case "github": {
                Utils.openWebpage("https://github.com/Open-RSC/Game");
                return;
            }
            case "minimize": {
                AppFrame.get().setState(1);
                return;
            }
            case "launch": {
                try {
                    ClientLauncher.launchClient();
                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                    e.printStackTrace();
                }
                return;
            }
            case "close": {
                System.exit(0);
                return;
            }
            default:
                break;
        }
        System.out.println(action);
    }
}
