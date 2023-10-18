
package telran.monitoring.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import telran.monitoring.documents.AvgPulseDoc;
import telran.monitoring.dto.AllValues;

public interface AvgPulseRepo extends MongoRepository<AvgPulseDoc, ObjectId> {

}