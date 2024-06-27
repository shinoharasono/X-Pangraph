package com.XPangraph;

import com.XPangraph.Screenshot.ProtractSave;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        // 执行截图功能
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    ProtractSave ps = new ProtractSave();
                    ps.setVisible(true);
                } catch (AWTException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
