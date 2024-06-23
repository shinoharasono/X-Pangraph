package com.XPangraph;

import com.XPangraph.Screenshot.ProtractSave;

import java.awt.*;

public class Main {
    public static void main(String[] args){
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