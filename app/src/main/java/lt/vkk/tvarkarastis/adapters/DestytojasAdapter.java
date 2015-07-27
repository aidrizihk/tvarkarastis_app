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
import lt.vkk.tvarkarastis.models.Destytojas;

public class DestytojasAdapter extends ArrayAdapter<Destytojas>{

    Context context;
    int layoutResourceId;
    List<Destytojas> data = null;

    public DestytojasAdapter(Context context, int resource, List<Destytojas> objects) {
        super(context, resource, objects);
        this.layoutResourceId = resource;
        this.context = context;
        this.data = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        DestytojasHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new DestytojasHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.txtTitle);

            row.setTag(holder);
        } else {
            holder = (DestytojasHolder) row.getTag();
        }

        Destytojas destytojas = data.get(position);
        holder.txtTitle.setText(destytojas.getPavarde() + ", " + destytojas.getVardas());

        return row;
    }

    static class DestytojasHolder {
        TextView txtTitle;
    }
}

