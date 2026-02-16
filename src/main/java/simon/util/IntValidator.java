package simon.util;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Utility class for validating indices and integers.
 */
public class IntValidator {

    /**
     * Validates a list of 1-based indices against the size of a list.
     *
     * @param indices List of 1-based indices to validate.
     * @param size The size of the list against which to validate the indices.
     * @return A set of valid indices if all are valid; null if any index is invalid.
     */
    public static Set<Integer> validateIndices(List<Integer> indices, int size) {
        // returns null if any index is invalid
        Set<Integer> validIndices = new LinkedHashSet<>();
        for (Integer idx : indices) {
            if (idx == null || idx <= 0 || idx > size) {
                return null;
            }
            validIndices.add(idx);
        }
        return validIndices;
    }
}
