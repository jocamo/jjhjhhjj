package com.example.profesor.ud4prac3.Adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.profesor.ud4prac3.Alumnos.Alumno;
import com.example.profesor.ud4prac3.R;

import java.util.ArrayList;

/**
 * Created by Profesor on 03/11/2015.
 */

public class ArrayAdapterAlumno extends ArrayAdapter<Alumno>  {

    public ArrayAdapterAlumno(Context contexto, ArrayList<Alumno> arrayAlumnos){
        //Lo primerísimo llamamos a papá para que haga lo suyo
        super(contexto,0,arrayAlumnos);
        //Cogemos arrayalumnos porque los vamos a necesitar
        //this.arrayAlumnos = arrayAlumnos;
    }

    //getView se va a llamar cada vez que necesite dibujar cada uno de los items de la ListView
    //Tendremos que sobrescribirlo para dibujarlo tal y como nosotros queremos
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //La primera vez que se ejecute necesitamos fijar aquí nuestro XML inflado.
        //Después ya no hará falta
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_listview, parent, false);
        }
        //Obtenemos el alumno correspondiente a la posición desde el arrayAdapter
        Alumno alumno = getItem(position);

        //Obtenemos todos los widgets del layout
        TextView textViewNombre = (TextView)convertView.findViewById(R.id.textView_nombre);
        TextView textViewApellido = (TextView)convertView.findViewById(R.id.textView_apellido);
        TextView textViewCurso = (TextView)convertView.findViewById(R.id.textView_curso);
        TextView textViewEdad = (TextView)convertView.findViewById(R.id.textView_edad);
        ImageView imageView = (ImageView)convertView.findViewById(R.id.imagenContacto);
        //Fijamos en cada uno el dato adecuado
        textViewNombre.setText(alumno.getNombre());
        textViewApellido.setText(alumno.getApellido());
        textViewCurso.setText(alumno.getCiclo());
        int edad = alumno.getEdad();
        textViewEdad.setText(String.valueOf(edad));
        if (edad > 25){
            textViewEdad.setTextColor(Color.RED);
        }
        else {
            textViewEdad.setTextColor(Color.BLUE);
        }
        if (alumno.isMujer()){
            imageView.setImageResource(R.drawable.contacto_mujer);
        }
        else {
            imageView.setImageResource(R.drawable.contacto_hombre);
        }
        //Devolvemos el convertView
        return convertView;
    }
}
