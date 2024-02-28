/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ontologia;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
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
                    // prin all properties

                    System.out.println("    "+s.getPredicate().getLocalName()+": "+s.getObject().toString());

                    
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

        //  Definição de classes e hierarquia
        //  utilizando a top-down:
        // projeto:
        // projeto interno
        // projeto externo
        // 	cargo:
        // diretor
        // diretor presidente
        // diretor financeiro
        // diretor RH
        // diretor marketing
        // diretor projetos
        // conselheiro
        // conselheiro presidente
        // conselheiro financeiro
        // conselheiro RH
        // conselheiro marketing
        // conselheiro projetos
        // 	diretoria:
        // diretoria presidencia
        // diretoria financeiro
        // diretoria rh
        // diretoria marketing
        // diretoria projetos

        // 	função:
        // desenvolvedor
        // front-end
        // back-end
        // full-stack
        // qa
        // devops
        // designer

        // 	associado

        OntClass projeto = model.createClass(NS+":"+"Projeto");
        OntClass projetoInterno = model.createClass(NS+":"+"ProjetoInterno");
        OntClass projetoExterno = model.createClass(NS+":"+"ProjetoExterno");
        projeto.addSubClass(projetoInterno);
        projeto.addSubClass(projetoExterno);

        OntClass cargo = model.createClass(NS+":"+"Cargo");
        OntClass diretor = model.createClass(NS+":"+"Diretor");
        OntClass diretorPresidente = model.createClass(NS+":"+"DiretorPresidente");
        OntClass diretorFinanceiro = model.createClass(NS+":"+"DiretorFinanceiro");
        OntClass diretorRH = model.createClass(NS+":"+"DiretorRH");
        OntClass diretorMarketing = model.createClass(NS+":"+"DiretorMarketing");
        OntClass diretorProjetos = model.createClass(NS+":"+"DiretorProjetos");
        OntClass conselheiro = model.createClass(NS+":"+"Conselheiro");
        OntClass conselheiroPresidente = model.createClass(NS+":"+"ConselheiroPresidente");
        OntClass conselheiroFinanceiro = model.createClass(NS+":"+"ConselheiroFinanceiro");
        OntClass conselheiroRH = model.createClass(NS+":"+"ConselheiroRH");
        OntClass conselheiroMarketing = model.createClass(NS+":"+"ConselheiroMarketing");
        OntClass conselheiroProjetos = model.createClass(NS+":"+"ConselheiroProjetos");
        cargo.addSubClass(diretor);
        cargo.addSubClass(conselheiro);
        diretor.addSubClass(diretorPresidente);
        diretor.addSubClass(diretorFinanceiro);
        diretor.addSubClass(diretorRH);
        diretor.addSubClass(diretorMarketing);
        diretor.addSubClass(diretorProjetos);
        conselheiro.addSubClass(conselheiroPresidente);
        conselheiro.addSubClass(conselheiroFinanceiro);
        conselheiro.addSubClass(conselheiroRH);
        conselheiro.addSubClass(conselheiroMarketing);
        conselheiro.addSubClass(conselheiroProjetos);

        OntClass diretoria = model.createClass(NS+":"+"Diretoria");
        OntClass diretoriaPresidencia = model.createClass(NS+":"+"DiretoriaPresidencia");
        OntClass diretoriaFinanceiro = model.createClass(NS+":"+"DiretoriaFinanceiro");
        OntClass diretoriaRH = model.createClass(NS+":"+"DiretoriaRH");
        OntClass diretoriaMarketing = model.createClass(NS+":"+"DiretoriaMarketing");
        OntClass diretoriaProjetos = model.createClass(NS+":"+"DiretoriaProjetos");
        diretoria.addSubClass(diretoriaPresidencia);
        diretoria.addSubClass(diretoriaFinanceiro);
        diretoria.addSubClass(diretoriaRH);
        diretoria.addSubClass(diretoriaMarketing);
        diretoria.addSubClass(diretoriaProjetos);

        OntClass funcao = model.createClass(NS+":"+"Funcao");
        OntClass desenvolvedor = model.createClass(NS+":"+"Desenvolvedor");
        OntClass frontEnd = model.createClass(NS+":"+"FrontEnd");
        OntClass backEnd = model.createClass(NS+":"+"BackEnd");
        OntClass fullStack = model.createClass(NS+":"+"FullStack");
        OntClass qa = model.createClass(NS+":"+"QA");
        OntClass devops = model.createClass(NS+":"+"DevOps");
        OntClass designer = model.createClass(NS+":"+"Designer");
        funcao.addSubClass(desenvolvedor);
        desenvolvedor.addSubClass(frontEnd);
        desenvolvedor.addSubClass(backEnd);
        desenvolvedor.addSubClass(fullStack);
        desenvolvedor.addSubClass(qa);
        desenvolvedor.addSubClass(devops);
        funcao.addSubClass(designer);

        OntClass associado = model.createClass(NS+":"+"Associado");

        OntClass reuniao = model.createClass(NS+":"+"Reuniao");

        OntClass meta = model.createClass(NS+":"+"Meta");

        // Definição das propriedades
        // projeto:
        //      nome
        //      data de início
        //      data de término
        //      gerente
        //      membros da equipe
        //      status
        // projeto externo:
        //      orçamento
        //      cliente
        // diretoria:
        //      nome
        //      membros da diretoria
        //      presidente
        //      data de inicio do mandato
        //      data de termino do mandato
        //      reuniões
        //      metas
        // associado:
        //      nome
        //      cargo da diretoria
        //      função
        //      data de ingresso
        //      habilidades/histórico (resume-rdf)
        //      trabalha em
        // reunião:
        //      data
        //      membros presentes
        //      tipo
        // meta
        //      data
        //      descrição
        //      foi alcançado

        DatatypeProperty nome = model.createDatatypeProperty(NS+":"+"Nome");
        DatatypeProperty dataInicio = model.createDatatypeProperty(NS+":"+"DataInicio");
        DatatypeProperty dataTermino = model.createDatatypeProperty(NS+":"+"DataTermino");
        DatatypeProperty status = model.createDatatypeProperty(NS+":"+"Status");
        ObjectProperty gerente = model.createObjectProperty(NS+":"+"Gerente");
        ObjectProperty membrosEquipe = model.createObjectProperty(NS+":"+"MembrosEquipe");
        model.add(projeto, nome, XSD.xstring);
        model.add(projeto, dataInicio, XSD.date);
        model.add(projeto, dataTermino, XSD.date);
        model.add(projeto, gerente, associado);
        model.add(projeto, membrosEquipe, associado);

        DatatypeProperty orcamento = model.createDatatypeProperty(NS+":"+"Orcamento");
        DatatypeProperty cliente = model.createDatatypeProperty(NS+":"+"Cliente");
        model.add(projetoExterno, orcamento, XSD.xfloat);
        model.add(projetoExterno, cliente, XSD.xstring);

        ObjectProperty membrosDiretoria = model.createObjectProperty(NS+":"+"MembrosDiretoria");
        ObjectProperty presidente = model.createObjectProperty(NS+":"+"Presidente");
        DatatypeProperty dataInicioMandato = model.createDatatypeProperty(NS+":"+"DataInicioMandato");
        DatatypeProperty dataTerminoMandato = model.createDatatypeProperty(NS+":"+"DataTerminoMandato");
        ObjectProperty reunioes = model.createObjectProperty(NS+":"+"Reunioes");
        ObjectProperty metas = model.createObjectProperty(NS+":"+"Metas");
        model.add(diretoria, membrosDiretoria, associado);
        model.add(diretoria, presidente, associado);
        model.add(diretoria, dataInicioMandato, XSD.date);
        model.add(diretoria, dataTerminoMandato, XSD.date);
        model.add(diretoria, reunioes, reuniao);
        model.add(diretoria, metas, meta);

        ObjectProperty cargoDiretoria = model.createObjectProperty(NS+":"+"CargoDiretoria");
        ObjectProperty funcaoAssociado = model.createObjectProperty(NS+":"+"FuncaoAssociado");
        DatatypeProperty dataIngresso = model.createDatatypeProperty(NS+":"+"DataIngresso");
        DatatypeProperty habilidades = model.createDatatypeProperty(NS+":"+"Habilidades");
        ObjectProperty trabalhaEm = model.createObjectProperty(NS+":"+"TrabalhaEm");
        model.add(associado, cargoDiretoria, cargo);
        model.add(associado, funcaoAssociado, funcao);
        model.add(associado, dataIngresso, XSD.date);
        model.add(associado, habilidades, XSD.xstring);
        model.add(associado, trabalhaEm, projeto);

        DatatypeProperty data = model.createDatatypeProperty(NS+":"+"Data");
        ObjectProperty membrosPresentes = model.createObjectProperty(NS+":"+"MembrosPresentes");
        DatatypeProperty tipo = model.createDatatypeProperty(NS+":"+"Tipo");
        model.add(reuniao, data, XSD.date);
        model.add(reuniao, membrosPresentes, associado);
        model.add(reuniao, tipo, XSD.xstring);

        DatatypeProperty descricao = model.createDatatypeProperty(NS+":"+"Descricao");
        DatatypeProperty foiAlcancado = model.createDatatypeProperty(NS+":"+"FoiAlcancado");
        model.add(meta, data, XSD.date);
        model.add(meta, descricao, XSD.xstring);
        model.add(meta, foiAlcancado, XSD.xboolean);


        // Definição de relação, tipo e cardinalidade de projeto:
        // nome
        // 		Descrição: representa o nome dado a um projeto
        // 		Domínio: Projeto
        // 		Range: String

        // orçamento
        // Descrição: representa o valor do projeto orçado como custo ao cliente do projeto.
        // Domínio: Projeto externo
        // Range: Number / float

        // cliente
        // Descrição: representa quem é o cliente/dono do projeto
        // Domínio: Projeto externo
        // Range: Pessoa (foaf)

        // data de início
        // Descrição: representa a data de início do projeto, quando foi alocada a equipe e iniciou o desenvolvimento
        // Domínio: Projeto
        // Range: Date

        // data de término
        // Descrição: representa a data de término do projeto, quando foi entregue ao cliente e terminado o desenvolvimento
        // Domínio: Projeto
        // Range: Date

        // gerente 
        // Descrição: representa o associado responsável pelo projeto, quem coordena os membros do projeto.
        // Domínio: Projeto
        // Range: Associado

        // membros da equipe
        // Descrição: representa os associados que estão desenvolvendo um projeto
        // Domínio: Projeto
        // Range: Lista de Associados

        // status
        // Descrição: representa o status atual do projeto
        // Domínio: Projeto
        // Range: String ou Enumeração (Ex: "planejando", "desenvolvendo", "finalizado").

        gerente.addDomain(projeto);
        gerente.addRange(associado);
        membrosEquipe.addDomain(projeto);
        membrosEquipe.addRange(associado);

        // diretoria:
        // 
        // nome
        // Descrição: representa o nome da diretoria
        // Domínio: Diretoria
        // Range: String

        // membros da diretoria
        // Descrição: representa os membros da diretoria
        // Domínio: Diretoria
        // Range: Lista de Associados

        // presidente
        // Descrição: representa o associado responsável pela diretoria.
        // Domínio: Diretoria
        // Range: Associado

        // data de início do mandato
        // Descrição: representa a data de início do mandato, quando a diretoria foi eleita
        // Domínio: Projeto
        // Range: Date

        // data de término do mandato
        // Descrição: representa a data de término do mandato, quando a diretoria precisa fazer nova eleição
        // Domínio: Projeto
        // Range: Date

        // reuniões
        // Descrição: representa as reuniões feitas e organizadas pela diretoria
        // Domínio: Diretoria
        // Range: Lista de reuniões

        // metas
        // Descrição: representa as metas planejadas pela diretoria.
        // Domínio: Diretoria
        // Range: Lista de metas

        presidente.addDomain(diretoria);
        presidente.addRange(associado);
        membrosDiretoria.addDomain(diretoria);
        membrosDiretoria.addRange(associado);
        reunioes.addDomain(diretoria);
        reunioes.addRange(reuniao);
        metas.addDomain(diretoria);
        metas.addRange(meta);

        // associado:
        // 
        // nome
        // Descrição: representa o nome do associado
        // Domínio: Associado
        // Range: String

        // cargo da diretoria
        // Descrição: representa qual cargo este associado possui dentro de uma diretoria, se for nulo, não possui cargo na diretoria
        // Domínio: Associado
        // Range: Cargo / nulo

        // função
        // Descrição: representa qual a função que o associado tem dentro da empresa.
        // Domínio: Associado
        // Range: Funcão

        // data de ingresso
        // Descrição: representa a data que o associado ingressou na empresa.
        // Domínio: Associado
        // Range: Date

        // habilidades/histórico (resume-rdf)

        // trabalha em
        // Descrição: representa a relação de qual projeto o associado trabalha.
        // Domínio: Associado
        // Range: Projeto

        cargoDiretoria.addDomain(associado);
        cargoDiretoria.addRange(cargo);
        funcaoAssociado.addDomain(associado);
        funcaoAssociado.addRange(funcao);
        trabalhaEm.addDomain(associado);
        trabalhaEm.addRange(projeto);

        //  reunião
        // data
        // Descrição: representa a data em que a reunião foi feita
        // Domínio: Reunião
        // Range: Date

        // membros presentes
        // Descrição: representa os associados presentes na reunião
        // Domínio: Reunião
        // Range: lista de Associados

        // tipo
        // Descrição: representa qual o intuito da reunião
        // Domínio: Reunião
        // Range: String / Enumeração (Ex: "Reunião semanal", "Reunião de alinhamento")

        membrosPresentes.addDomain(reuniao);
        membrosPresentes.addRange(associado);

        // meta
        // data
        // Descrição: representa qual a data para a meta ser alcançada
        // Domínio: Meta
        // Range: Date

        // descrição
        // Descrição: representa a descrição da meta, qual o objetivo que deve ser alcançado
        // Domínio: Meta
        // Range: String
        // foi alcançado
        // Descrição: representa se a meta foi alcançada
        // Domínio: Meta
        // Range: Boolean

        //  criar instâncias
        Individual partamon = model.createIndividual(NS+":"+"Partamon", projetoExterno);
        partamon.addProperty(nome, "Partamon");
        partamon.addProperty(dataInicio, "01/01/2019");
        partamon.addProperty(dataTermino, "01/01/2020");
        partamon.addProperty(orcamento, "100000");
        partamon.addProperty(cliente, "Partamon");
        Individual augusto = model.createIndividual(NS+":"+"Augusto", associado);

        augusto.addProperty(nome, "Augusto");
        augusto.addProperty(cargoDiretoria, diretorProjetos);
        augusto.addProperty(funcaoAssociado, frontEnd);
        augusto.addProperty(dataIngresso, "01/01/2019");
        augusto.addProperty(habilidades, "Desenvolvedor Full-Stack");
        augusto.addProperty(trabalhaEm, partamon);
        partamon.addProperty(gerente, augusto);
        partamon.addProperty(membrosEquipe, augusto);

        Individual dirProjetos = model.createIndividual(NS+":"+"Diretoria de Projetos", diretoriaProjetos);
        dirProjetos.addProperty(nome, "Diretoria de Projetos");
        dirProjetos.addProperty(presidente, augusto);
        dirProjetos.addProperty(dataInicioMandato, "01/01/2019");
        dirProjetos.addProperty(dataTerminoMandato, "01/01/2020");
        dirProjetos.addProperty(membrosDiretoria, augusto);

        // cria um arquivo Empresa.owl
        
        File file = new File("hut.owl");
        
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
