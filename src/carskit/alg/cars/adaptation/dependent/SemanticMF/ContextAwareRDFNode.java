package carskit.alg.cars.adaptation.dependent.SemanticMF;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mohammad Pourzaferani on 12/21/16.
 */
public class ContextAwareRDFNode {
    boolean actived= false;
    String URI;
    Multimap<String,String> child = ArrayListMultimap.create();
    Map<String,Integer> contexts = new HashMap();
}
