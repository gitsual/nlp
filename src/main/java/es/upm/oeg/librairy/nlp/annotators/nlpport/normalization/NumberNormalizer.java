package es.upm.oeg.librairy.nlp.annotators.nlpport.normalization;


import es.upm.oeg.librairy.nlp.annotators.nlpport.replacement.Replacement;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * This class ...
 *
 * @author   Ricardo Rodrigues
 * @version  0.9.9.9
 */
public class NumberNormalizer extends Normalizer {
  private Pattern[] declensionExceptions = null;
  private Pattern[] declensionTargets = null;
  private Pattern[] declensionTags = null;
  private Replacement[] declensions = null;

  /**
   * Creates a new <code>NumberNormalizer</code> object ...
   * 
   * @param  declensions ...
   */
  public NumberNormalizer(Replacement[] declensions) {
    this.declensions = declensions;
    Arrays.sort(this.declensions);
    declensionExceptions = new Pattern[this.declensions.length];
    declensionTargets = new Pattern[this.declensions.length];
    declensionTags = new Pattern[this.declensions.length];
    for (int i = 0; i < declensions.length; i++) {
      declensionExceptions[i] = Pattern.compile(declensions[i].getExceptions());
      declensionTargets[i] = Pattern.compile(declensions[i].getPrefix()
          + declensions[i].getTarget() + declensions[i].getSuffix());
      declensionTags[i] = Pattern.compile(declensions[i].getTag());
    }
  }

  /**
   * This method retrieves the singular form of a given token, if it exists,
   * when classified with a given <em>PoS tag</em>. Otherwise, it returns the
   * same token (in lower case).
   *
   * @param  token the token whose lemma is wanted
   * @param  tag the <em>PoS tag</em> of the token
   * @return the singular form of the token (when with the given tag)
   */
  public String normalize(String token, String tag) {
    String normalization = token.toLowerCase();
    for (int i = 0; i < declensions.length; i++) {
      if (declensionTargets[i].matcher(normalization).matches()
          && declensionTags[i].matcher(tag.toLowerCase()).matches()
          && !declensionExceptions[i].matcher(normalization).matches()) {
        normalization = normalization.substring(0,
            normalization.length() - declensions[i].getTarget().length())
            + declensions[i].getReplacement();
        break;
      }
    }
    return normalization;
  }
}
