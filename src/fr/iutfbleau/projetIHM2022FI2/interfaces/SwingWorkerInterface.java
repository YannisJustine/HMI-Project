package fr.iutfbleau.projetIHM2022FI2.interfaces;

/**
 * Interface utilisé par un {@link fr.iutfbleau.projetIHM2022FI2.view.custom.worker.CustomWorker SwingWorker} pour exécuter en arrière-plan des fonctions
 */
public interface SwingWorkerInterface {
    /**
     * Fonction a exécuter en fond
     */
    public void whenBackground();
    /**
     * Fonction a exécuter après avoir fini la tâche de fond
     */
    public void whenDone();
}
