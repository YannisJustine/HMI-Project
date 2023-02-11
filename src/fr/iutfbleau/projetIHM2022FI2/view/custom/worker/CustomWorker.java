package fr.iutfbleau.projetIHM2022FI2.view.custom.worker;

import javax.swing.SwingWorker;

import fr.iutfbleau.projetIHM2022FI2.interfaces.SwingWorkerInterface;

public class CustomWorker extends SwingWorker<Void,Void>{

    private SwingWorkerInterface object;

    public CustomWorker(SwingWorkerInterface object){
        this.object = object;
    }

    @Override
    protected Void doInBackground() throws Exception {
        this.object.whenBackground();
        return null;
    }
    
    @Override
    protected void done() {
        this.object.whenDone();
    }
}
