/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ontologia;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.XSD;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Win10
 */
public class Ontologia {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        criarOntologia();
        verOntologia();
        
//        Property salarioAugusto = model.getProperty(NS+":"+"Salario");
//        
//        Individual augusto = model.getIndividual(NS+":"+"Augusto");
//        Literal novoSalario = model.createTypedLiteral(4000);
//        augusto.setPropertyValue(salarioAugusto, novoSalario);
        
//        verOntologia();
//        Individual joao = model.createIndividual(NS+":"+"Joao", funcionario);
//        joao.setPropertyValue(gerenteDeProjeto, joao);
//        Individual sistema = model.createIndividual(NS+":"+"Sistema", projetoInterno);
//        Individual tech = model.createIndividual(NS+":"+"Tecnologia", divisao);
//        joao.addProperty(trabalhaEm, sistema);
//        joao.addProperty(trabalhaPara, tech);
        
    }
    
    public static void verOntologia(){
        System.out.println(getRoot(model));
        System.out.println(" ");
        
        for(Iterator<OntClass> i = model.listClasses(); i.hasNext();){
            OntClass cls = i.next();
            
            System.out.println(cls.getLocalName()+": ");
            for(Iterator it = cls.listInstances(true);it.hasNext();){
                Individual ind = (Individual) it.next();
                System.out.println("  "+ind.getLocalName()+"  ");
                for(StmtIterator j = ind.listProperties();j.hasNext();){
                    Statement s = j.next();
                    if(s.getObject().isLiteral()){
                        System.out.print("        "+s.getPredicate().getLocalName()+ " -> ");
                        System.out.println(s.getLiteral().getLexicalForm());
                    }
                    
                }
                
                
            }
            System.out.println("");
            
        }
        
    }
    
    public static String getRoot(OntModel model){
        for(Iterator<OntClass> i = model.listClasses(); i.hasNext();){
            OntClass cls = i.next();
            if(cls.isHierarchyRoot()){
                return cls.toString();
            }
        }
        return null;
    }
    
    public static void criarOntologia(){
        model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
        
        NS = "Empresa";
        
        // funcionarios
        OntClass funcionario = model.createClass(NS + ":" + "Funcionario");
        DatatypeProperty cargo = model.createDatatypeProperty(NS+":"+"Cargo");

        DatatypeProperty gerenteDeProjeto = model.createDatatypeProperty(cargo+":"+"Gerente_de_projeto");
        DatatypeProperty analistaDeNegocios = model.createDatatypeProperty(cargo+":"+"Analista_de_negocios");
        DatatypeProperty arquitetoDeSistemas = model.createDatatypeProperty(cargo+":"+"Arquiteto_de_sistemas");
        DatatypeProperty testador =model.createDatatypeProperty(cargo + ":" + "Testador");
        DatatypeProperty desenvolvedor=model.createDatatypeProperty(cargo + ":" + "Desenvolvedor");
        DatatypeProperty administradorBancoDados=model.createDatatypeProperty(cargo + ":" + "Administrador"); 
        
        cargo.addSubProperty(gerenteDeProjeto);
        cargo.addSubProperty(analistaDeNegocios);
        cargo.addSubProperty(arquitetoDeSistemas);
        cargo.addSubProperty(testador);
        cargo.addSubProperty(desenvolvedor);
        cargo.addSubProperty(administradorBancoDados);
        
        cargo.addDomain(funcionario);
        
        OntClass projeto = model.createClass(NS+":"+"Projeto");
        OntClass projetoInterno = model.createClass(projeto + ":" + "Projeto_interno");
        OntClass projetoExterno = model.createClass(projeto + ":" + "Projeto_externo");
        
        projeto.addSubClass(projetoInterno);
        projeto.addSubClass(projetoExterno);


        OntClass divisao = model.createClass(NS+":"+"Divisao");
        
        DatatypeProperty trabalhaPara = model.createDatatypeProperty(divisao + ":" + "Trabalha_para");
        DatatypeProperty trabalhaEm = model.createDatatypeProperty(divisao + ":" + "Trabalha_em");
        
        model.add(funcionario, trabalhaPara, divisao);
        model.add(funcionario, trabalhaEm, projeto);


        Individual joao = model.createIndividual(NS+":"+"Joao", funcionario);
        joao.setPropertyValue(gerenteDeProjeto, joao);
        Individual sistema = model.createIndividual(projetoInterno+":"+"Sistema", projetoInterno);
        Individual tech = model.createIndividual(divisao+":"+"Tecnologia", divisao);
        joao.addLiteral(trabalhaEm, sistema);
        joao.addLiteral(trabalhaPara, tech);

        
//        // individuos
//        
//        Individual ana = model.createIndividual(NS+":"+"Ana",gerenteDeVendas);
//        Literal telefoneAna = model.createTypedLiteral("7788888888");
//        ana.setPropertyValue(telefone, telefoneAna);
//        Literal salarioAna = model.createTypedLiteral(5000);
//        ana.setPropertyValue(salario, salarioAna);
//        
//        Individual luiz = model.createIndividual(NS+":"+"Luiz",vendedor);
//        Literal telefoneLuiz = model.createTypedLiteral("7799999999");
//        luiz.setPropertyValue(telefone, telefoneLuiz);
//        luiz.setPropertyValue(salario,model.createTypedLiteral(1000));
//        
//        Individual paulo = model.createIndividual(NS+":"+"Paulo",representante);
//        paulo.setPropertyValue(telefone, model.createTypedLiteral("7777777777"));
//        paulo.setPropertyValue(salario,model.createTypedLiteral(1000));
//        paulo.setPropertyValue(comissao,model.createTypedLiteral(0.05));
//        
//        Individual joao = model.createIndividual(NS+":"+"Joao",gerenteDeProducao);
//        joao.setPropertyValue(telefone, model.createTypedLiteral("7766666666"));
//        joao.setPropertyValue(salario,model.createTypedLiteral(4000));
//        
//        Individual augusto = model.createIndividual(NS+":"+"Augusto",operador);
//        augusto.setPropertyValue(telefone, model.createTypedLiteral("7755555555"));
//        augusto.setPropertyValue(salario,model.createTypedLiteral(1200));
//        augusto.setPropertyValue(meta,model.createTypedLiteral(2200));
//        
//        Individual rafael = model.createIndividual(NS+":"+"Rafael",embalador);
//        rafael.setPropertyValue(telefone, model.createTypedLiteral("7744444444"));
//        rafael.setPropertyValue(salario,model.createTypedLiteral(1100));
//        rafael.setPropertyValue(meta,model.createTypedLiteral(4400));
        
        File file = new File("Empresa.owl");
        
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Ontologia.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        try {
            model.write(new PrintWriter(file));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Ontologia.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Criado!");
        
    }
    
    static OntModel model;
    static String NS;
}
