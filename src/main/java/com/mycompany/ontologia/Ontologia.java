/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ontologia;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;

/**
 *
 * @author lsferreira
 */
public class Ontologia {

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
    
    public static void criarOntologia(){
        model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
    
           NS = "Empresa";
           
           OntClass Funcionario = model.createClass(NS + ":" + "Funcionario");
           
    }
    
    static OntModel model;
    static String NS;
}
