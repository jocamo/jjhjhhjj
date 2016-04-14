package com.example.profesor.ud4prac3.Fragmentos;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.profesor.ud4prac3.Adaptadores.ArrayAdapterAlumno;
import com.example.profesor.ud4prac3.Alumnos.Alumno;
import com.example.profesor.ud4prac3.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoMenu extends Fragment implements ListView.OnItemClickListener{

    private ListView listView;
    private FragmentoMenuListener escuchador;
    ArrayAdapter<Alumno> arrayAdapterCustom;

    public FragmentoMenu() {
        // Required empty public constructor
    }

    //En este método instanciamos e iniciamos los elementos gráficos
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_fragmento_menu, container, false);

        //Instanciamos el listView de nuestro fragmento
        listView = (ListView)layout.findViewById(R.id.listView);
        //Nos erigimos en oyentes del listView
        listView.setOnItemClickListener(this);
        return layout;
    }
    //Según el botón presionado llamaremos a los métodos declarados en la interface
    //estos métodos serán implementados desde fuera para poder ser reutilizables.
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Alumno alumno = (Alumno)parent.getItemAtPosition(position);
        escuchador.onAlumnoPulsado(alumno);
    }

    //Interface donde publicaremos qué métodos callback debe
    //implementar la actividad que nos contenga.
    public interface FragmentoMenuListener {
        //Este método se llamará cuando se presiones un alumno pasándole el amulno presionado
        //Es una callback y será implementada por la actividad que contenga el fragment
        void onAlumnoPulsado(Alumno alumno);
    }
    //Con este método permitiremos que la Activity escuche nuestros
    //callbacks
    public void setFragmentoMenuListener (FragmentoMenuListener escuchador){
        this.escuchador=escuchador;
    }
    //Métodos que expondremos a la activity para que nos diga qué quiere de nosotros
    //En este caso será donde nos pase la lista de alumnos con la que construiremos
    //el listView
    public void setAlumnos(ArrayList<Alumno> arrayAlumnos){
        arrayAdapterCustom =
                new ArrayAdapterAlumno(getActivity(),arrayAlumnos);
        listView.setAdapter(arrayAdapterCustom);
    }
    //Si un alumno cambia llamaremos a este método para que lo actualice en el menú
    public void modificaAlumnoEnMenu(Alumno alumno) {
        //Borramos el alumno del arrayAdapterCustom
        arrayAdapterCustom.remove(arrayAdapterCustom.getItem(alumno.getPosicion()));
        //Ponemos al nuevo en su lugar
        arrayAdapterCustom.insert(alumno, alumno.getPosicion());
        //Le informamos de que hemos cambiado un alumno
        arrayAdapterCustom.notifyDataSetChanged();
    }
}


