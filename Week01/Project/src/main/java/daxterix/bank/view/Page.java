package daxterix.bank.view;

import daxterix.bank.view.Utils.InputUtils;

public abstract class Page {
    protected abstract Page _run();

    public void run() {
        Page page = this;
        do {
            InputUtils.printPageTitle(page);
            page = page._run();
        }
        while(page != null);
    }

    public abstract String getTitle();
}
