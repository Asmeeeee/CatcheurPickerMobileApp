package fr.maxime.catcheurpicker.Tools;

import android.view.View;

public interface InterfaceGestionClick {
    void onItemClick(int position, View v);
    void onItemLongClick(int position, View view);
}
