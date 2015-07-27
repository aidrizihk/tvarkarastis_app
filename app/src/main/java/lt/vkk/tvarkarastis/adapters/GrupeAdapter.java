package lt.vkk.tvarkarastis.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import lt.vkk.tvarkarastis.R;
import lt.vkk.tvarkarastis.models.Grupe;


public class GrupeAdapter extends ArrayAdapter<Grupe> {

    Context context;
    int layoutResourceId;
    List<Grupe> data = null;

    public GrupeAdapter(Context context, int resource, List<Grupe> objects) {
        super(context, resource, objects);
        this.layoutResourceId = resource;
        this.context = context;
        this.data = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        GrupeHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new GrupeHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.txtTitle);

            row.setTag(holder);
        } else {
            holder = (GrupeHolder) row.getTag();
        }

        Grupe grupe = data.get(position);
        holder.txtTitle.setText(grupe.getPavadinimas());

        return row;
    }

    static class GrupeHolder {
        TextView txtTitle;
    }
}
