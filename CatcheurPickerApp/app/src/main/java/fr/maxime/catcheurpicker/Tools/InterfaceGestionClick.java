package fr.maxime.catcheurpicker.Tools;

import android.view.View;

import java.util.concurrent.ExecutionException;

public interface InterfaceGestionClick {
    void onItemClick(int position, View v) throws ExecutionException, InterruptedException;
    void onItemLongClick(int position, View view);
}
