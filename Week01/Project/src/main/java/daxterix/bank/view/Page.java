package daxterix.bank.view;

import daxterix.bank.view.Utils.InputUtils;

public abstract class Page {
    protected abstract Page _run();

    public void run() {
        InputUtils.printPageTitle(this);
        _run();
    }

    public abstract String getTitle();
}
