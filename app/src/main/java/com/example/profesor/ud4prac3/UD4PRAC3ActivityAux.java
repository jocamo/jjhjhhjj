package com.example.profesor.ud4prac3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.profesor.ud4prac3.Alumnos.Alumno;
import com.example.profesor.ud4prac3.Fragmentos.FragmentoDetalle;

public class UD4PRAC3ActivityAux extends AppCompatActivity implements FragmentoDetalle.FragmentoDetalleListener {


    private Alumno alumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ud3_prac2_activity_aux);

        //Instanciamos el fragmentoMenu desde el layout de la actividad
        //para después erigirnos como oyentes de sus callback. (Guardar alumno)
        FragmentoDetalle fragmento = (FragmentoDetalle) getFragmentManager().findFragmentById(R.id.fragment_detalle);
        fragmento.setFragmentoDetalleListener(this);

        //Recuperamos datos del Bundle pasado en el Intent que nos ha pasado la actividad que nos
        // llamó. En este Bundle se hallará el alumno que queremos mostrar/modificar
        Bundle extras = getIntent().getExtras();
        Alumno alumno = extras.getParcelable(UD4PRAC3ActivityMain.KEY_REQUEST);

        fragmento.setAlumno(alumno);
    }

    //Implementamos el método expuesto por la interface del fragmentoDettalle
    //Este método callback se ejecutará cuando el dato haya sido introducido
    //en el fragmentoMenu.
    @Override
    public void onGuardar(Alumno alumno) {
        //Creamos en Bundle, Devolvemos el dato y finalizamos la actividad con setResult() y finish()
        Intent intent = new Intent();
        Bundle b = new Bundle();
        b.putParcelable(UD4PRAC3ActivityMain.KEY_RESULT, alumno);
        intent.putExtras(b);
        setResult(UD4PRAC3ActivityMain.CODE_RESULT_OK, intent);
        finish();
    }

}