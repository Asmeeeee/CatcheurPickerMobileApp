package fr.maxime.catcheurpicker.Tools;

import android.view.View;

import java.util.concurrent.ExecutionException;

public interface InterfaceGestionClick {
    void onItemClick(int position, View v) throws ExecutionException, InterruptedException;
    void onItemLongClick(int position, View view);
    void onItemClickDelete(int position, View v);
    void onItemModifier(int position, View v);
}
