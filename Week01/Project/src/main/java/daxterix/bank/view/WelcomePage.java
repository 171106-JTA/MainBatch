package daxterix.bank.view;

import daxterix.bank.view.Utils.InputUtils;

public class WelcomePage extends Page{

    @Override
    protected Page _run() {
        InputUtils.readLine("key-press to continue");
        return null;
    }

    @Override
    public String getTitle() {
        return "Welcome to the <insert suitable bank title> Bank";
    }
}
