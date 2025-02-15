package es.upm.oeg.librairy.nlp.annotators.dbpedia;

import org.librairy.service.nlp.facade.model.Annotation;
import org.librairy.service.nlp.facade.model.PoS;

import java.util.List;

/**
 * @author Badenes Olmedo, Carlos <cbadenes@fi.upm.es>
 */
public interface DBpediaAnnotator {

    List<Annotation> annotate (String text, List<PoS> filter);


}
