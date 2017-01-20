package de.kdld16.hpi.transforms;

import de.kdld16.hpi.util.DBPediaHelper;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.Partition;
import org.apache.beam.sdk.values.KV;

/**
 * Created by jonathan on 22.11.16.
 */
public class PartitionByDataset implements Partition.PartitionFn<String> {

    public int partitionFor(String fact, int numPartitions) {
        if (fact.contains("rdf:type")) {
            return 0;
        }
        String[] split = fact.split(" ",3);
        if (split[2].contains(DBPediaHelper.wikidataPrefix)) {
            return 1;
        }
        return 2;
    }
}
