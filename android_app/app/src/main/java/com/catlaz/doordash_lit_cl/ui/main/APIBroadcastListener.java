package com.catlaz.doordash_lit_cl.ui.main;

/**
 * Interface used by the APIUpdateBroadcastReceiver to implement a callback to update the
 * associated UI
 *
 * @author Caterina Lazaro
 * @version 1.0 Jan 2021
 */
public interface APIBroadcastListener {

    /**
     * Method to update UI components
     */
    default void updateUI() {
    }
}
