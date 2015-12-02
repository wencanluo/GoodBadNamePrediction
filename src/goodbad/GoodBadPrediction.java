package goodbad;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;

import util.Fio;
import weka.core.Instances;
import nlp.InstancesPair;
import nlp.StringVectorWrapper;
import nlp.WekaWrapper;

public class GoodBadPrediction {
	
	public static void CrossValidation(String data) throws Exception {
		Instances dataset = new Instances(new BufferedReader(new FileReader(data+".arff")));
		dataset.setClassIndex(dataset.numAttributes()-1);

		System.out.println("train:" + data);
		System.out.println("train: " + dataset.numInstances());
		
		StringVectorWrapper ngramWrapper = new StringVectorWrapper();
		WekaWrapper wekaWapper = new WekaWrapper();
		
		dataset = ngramWrapper.ApplyStringVectorFilter(dataset, "Text");
		
		dataset.setClassIndex(dataset.attribute("@class@").index());
		wekaWapper.SaveInstances(dataset, data + "_ngram.arff");
		
		wekaWapper.labelfile = data + ".label";
		
		wekaWapper.Crossvalidataion(dataset, 10, true);
	}
	
	public static void TrainTest(String train, String test) throws Exception {
		Instances trainset = new Instances(new BufferedReader(new FileReader(train+".arff")));
		trainset.setClassIndex(trainset.numAttributes()-1);
		
		Instances testset = new Instances(new BufferedReader(new FileReader(test+".arff")));
		testset.setClassIndex(testset.numAttributes()-1);
		
		System.out.println("train:" + train);
		System.out.println("train: " + trainset.numInstances());
		System.out.println("test: " + testset.numInstances());
		
		StringVectorWrapper ngramWrapper = new StringVectorWrapper();
		WekaWrapper wekaWapper = new WekaWrapper();
		
		InstancesPair p = ngramWrapper.ApplyStringVectorFilter(trainset, "Text", testset);
		trainset = p.a;
		testset = p.b;
		
		wekaWapper.SaveInstances(trainset, train + "_ngram.arff");
		wekaWapper.SaveInstances(testset, test + "_ngram.arff");
		
		wekaWapper.labelfile = test + ".label";
		wekaWapper.TrianTest(trainset, testset);
	}
	
	public static void H1() throws Exception{
		String datas[] = {
				//"../good-bad-names-for-GN/data/weka/NetiNeti",
				//"../good-bad-names-for-GN/data/weka/TaxonFinder",
				//"../good-bad-names-for-GN/data/weka/NetiNeti_TaxonFinder",
				//"../good-bad-names-for-GN/data/weka/has_question_mark",
				//"../good-bad-names-for-GN/data/weka/all_caplitized",
				//"../good-bad-names-for-GN/data/weka/NetiNeti_TaxonFinder_has_question_mark_all_caplitized",
				//"../good-bad-names-for-GN/data/weka/parse",
				//"../good-bad-names-for-GN/data/weka/parse_netiti_taxonfinder",
				//"../good-bad-names-for-GN/data/weka/misspelling",
				//"../good-bad-names-for-GN/data/weka/parse_netiti_taxonfinder_misspelling",
				//"../good-bad-names-for-GN/data/weka/authorship",
				//"../good-bad-names-for-GN/data/weka/parse_netiti_taxonfinder_misspelling_authorship",
				
				//"../good-bad-names-for-GN/data/weka/classification",
				//"../good-bad-names-for-GN/data/weka/parse_netiti_taxonfinder_misspelling_authorship_classification",
				"../good-bad-names-for-GN/data/weka/classification_parser",
				
		};
		
		for(String data: datas){
			GoodBadPrediction classifier = new GoodBadPrediction();
			classifier.CrossValidation(data);
		}
	}
	
	public static void H2() throws Exception{
		String train = "../good-bad-names-for-GN/data/weka/parse_netiti_taxonfinder_misspelling_authorship_classification_gn_all";
		String folder = "../good-bad-names-for-GN/data/weka/names/";
		
		int i = 0;
		
		while(true){
			String test = folder + Integer.toString(i);
			String test_fullname = test + ".arff";
			System.out.print(test_fullname);
			
			if (!Fio.isFileExist(test_fullname)) break;
			
			GoodBadPrediction classifier = new GoodBadPrediction();
			classifier.TrainTest(train, test);
			
			i++;
		}
	}
	
	public static void H3() throws Exception{
		String train = "../good-bad-names-for-GN/data/weka/parse_netiti_taxonfinder_misspelling_authorship_classification_gn";
		String test = "../good-bad-names-for-GN/data/weka/gn_test";
			
		GoodBadPrediction classifier = new GoodBadPrediction();
		classifier.TrainTest(train, test);
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		PrintStream oldout = System.out;
		File f = new File("log.txt");		
		//System.setOut(new PrintStream(f));
		
		//H1();
		H2();
		//H3();
		
		System.out.println("Done!");
	}

}
