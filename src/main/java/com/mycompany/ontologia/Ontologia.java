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
        
        Property salarioAugusto = model.getProperty(NS+":"+"Salario");
        
        Individual augusto = model.getIndividual(NS+":"+"Augusto");
        Literal novoSalario = model.createTypedLiteral(4000);
        augusto.setPropertyValue(salarioAugusto, novoSalario);
        
        verOntologia();
        
    }
    
    public static void verOntologia(){
        System.out.println(getRoot(model));
        
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
        OntClass gerenteDeVendas = model.createClass(NS + ":" + "Gerente_de_vendas");
        OntClass vendedor = model.createClass(NS + ":" + "Vendedor");
        OntClass representante = model.createClass(NS + ":" + "Representante");
        OntClass gerenteDeProducao = model.createClass(NS + ":" + "Gerente_de_Produção");
        OntClass operador = model.createClass(NS + ":" + "Operador");
        OntClass embalador = model.createClass(NS + ":" + "Embalador");
        
        funcionario.addSubClass(gerenteDeVendas);
        funcionario.addSubClass(gerenteDeProducao);
        gerenteDeVendas.addSubClass(vendedor);
        gerenteDeVendas.addSubClass(representante);
        gerenteDeProducao.addSubClass(operador);
        gerenteDeProducao.addSubClass(embalador);

        DatatypeProperty telefone = model.createDatatypeProperty(NS+":"+"Telefone");
        telefone.addRange(XSD.xstring);
        telefone.addDomain(funcionario);
        
        DatatypeProperty salario = model.createDatatypeProperty(NS+":"+"Salario");
        salario.addRange(XSD.xint);
        salario.addDomain(funcionario);
        
        DatatypeProperty meta = model.createDatatypeProperty(NS+":"+"Meta");
        meta.addRange(XSD.xfloat);
        meta.addDomain(operador);
        meta.addDomain(embalador);
        
        DatatypeProperty comissao = model.createDatatypeProperty(NS+":"+"Comissao");
        comissao.addRange(XSD.xfloat);
        comissao.addDomain(representante);

        // projetos
        OntClass projeto = model.createClass(NS + ":" + "Projeto");
        OntClass projetoInterno = model.createClass(NS + ":" + "Projeto_Interno");
        OntClass projetoExterno = model.createClass(NS + ":" + "Projeto_Externo");
        
        projeto.addSubClass(projetoInterno);
        projeto.addSubClass(projetoExterno);
        
        DatatypeProperty nomeProjeto = model.createDatatypeProperty(NS + ":" + "Nome_Projeto");
        nomeProjeto.addRange(XSD.xstring);
        nomeProjeto.addDomain(projeto);
        
        DatatypeProperty dataInicio = model.createDatatypeProperty(NS + ":" + "Data_Inicio");
        dataInicio.addRange(XSD.xstring);
        dataInicio.addDomain(projeto);
        
        DatatypeProperty dataFim = model.createDatatypeProperty(NS + ":" + "Data_Fim");
        dataFim.addRange(XSD.xstring);
        dataFim.addDomain(projeto);
        
        DatatypeProperty status = model.createDatatypeProperty(NS + ":" + "Status");
        status.addRange(XSD.xstring);
        status.addDomain(projeto);

        // divisao (RH, Financeiro, Producao)
        OntClass divisao = model.createClass(NS + ":" + "Divisao");
        OntClass rh = model.createClass(NS + ":" + "RH");
        OntClass financeiro = model.createClass(NS + ":" + "Financeiro");
        OntClass producao = model.createClass(NS + ":" + "Producao");

        divisao.addSubClass(rh);
        divisao.addSubClass(financeiro);
        divisao.addSubClass(producao);

        DatatypeProperty nomeDivisao = model.createDatatypeProperty(NS + ":" + "Nome_Divisao");
        nomeDivisao.addRange(XSD.xstring);
        nomeDivisao.addDomain(divisao);

        
        // individuos
        
        Individual ana = model.createIndividual(NS+":"+"Ana",gerenteDeVendas);
        Literal telefoneAna = model.createTypedLiteral("7788888888");
        ana.setPropertyValue(telefone, telefoneAna);
        Literal salarioAna = model.createTypedLiteral(5000);
        ana.setPropertyValue(salario, salarioAna);
        
        Individual luiz = model.createIndividual(NS+":"+"Luiz",vendedor);
        Literal telefoneLuiz = model.createTypedLiteral("7799999999");
        luiz.setPropertyValue(telefone, telefoneLuiz);
        luiz.setPropertyValue(salario,model.createTypedLiteral(1000));
        
        Individual paulo = model.createIndividual(NS+":"+"Paulo",representante);
        paulo.setPropertyValue(telefone, model.createTypedLiteral("7777777777"));
        paulo.setPropertyValue(salario,model.createTypedLiteral(1000));
        paulo.setPropertyValue(comissao,model.createTypedLiteral(0.05));
        
        Individual joao = model.createIndividual(NS+":"+"Joao",gerenteDeProducao);
        joao.setPropertyValue(telefone, model.createTypedLiteral("7766666666"));
        joao.setPropertyValue(salario,model.createTypedLiteral(4000));
        
        Individual augusto = model.createIndividual(NS+":"+"Augusto",operador);
        augusto.setPropertyValue(telefone, model.createTypedLiteral("7755555555"));
        augusto.setPropertyValue(salario,model.createTypedLiteral(1200));
        augusto.setPropertyValue(meta,model.createTypedLiteral(2200));
        
        Individual rafael = model.createIndividual(NS+":"+"Rafael",embalador);
        rafael.setPropertyValue(telefone, model.createTypedLiteral("7744444444"));
        rafael.setPropertyValue(salario,model.createTypedLiteral(1100));
        rafael.setPropertyValue(meta,model.createTypedLiteral(4400));
        
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
