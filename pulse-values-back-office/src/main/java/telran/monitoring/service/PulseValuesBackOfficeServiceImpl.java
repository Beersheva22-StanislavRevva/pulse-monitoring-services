package telran.monitoring.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.monitoring.documents.AvgPulseDoc;
import telran.monitoring.dto.AllValues;
import telran.monitoring.repo.AvgPulseRepo;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.SortOperation.*;

@Service
@RequiredArgsConstructor
public class PulseValuesBackOfficeServiceImpl implements PulseValuesBackOfficeService {
	
	final AvgPulseRepo avgPulseRepo;
	
	final MongoTemplate mongoTemplate;
	

	@Override
	public int getAvgValue(long patientId, LocalDateTime from, LocalDateTime to) {
		int res = 0;
		MatchOperation matchOperation = match(Criteria.where("patientId").is(patientId).and("dateTime").gte(from).lte(to));
		GroupOperation groupOperation = group().avg("value").as("avg");
		Aggregation pipeLine = newAggregation(List.of(matchOperation, groupOperation));
		var aggregationResult = mongoTemplate.aggregate(pipeLine, AvgPulseDoc.class,Document.class);
		try {
			double resDouble = aggregationResult.getUniqueMappedResult().getDouble("avg");
			return (int) Math.round(resDouble);
		} catch (Exception e) {
			return res;
		}
	}

	@Override
	public int getMaxValue(long patientId, LocalDateTime from, LocalDateTime to) {
		int res = 0;
		MatchOperation matchOperation = match(Criteria.where("patientId").is(patientId).and("dateTime").gte(from).lte(to));
		SortOperation sortOperation = sort(Direction.DESC, "value");
		LimitOperation limitOperation = limit(1);
		Aggregation pipeLine = newAggregation(List.of(matchOperation, sortOperation, limitOperation));
		var aggregationResult = mongoTemplate.aggregate(pipeLine, AvgPulseDoc.class,Document.class);
		try {
			res = aggregationResult.getUniqueMappedResult().getInteger("value");
			return res;
		} catch (Exception e) {
			return res;
		}
	}

	@Override
	public List<Integer> getAllValues(long patientId, LocalDateTime from, LocalDateTime to) {
		MatchOperation matchOperation = match(Criteria.where("patientId").is(patientId).and("dateTime").gte(from).lte(to));
		Aggregation pipeLine = newAggregation(List.of(matchOperation));
		var aggregationResult = mongoTemplate.aggregate(pipeLine, AvgPulseDoc.class,Document.class);
		List<Document> resultDocuments = aggregationResult.getMappedResults();
		return resultDocuments.stream().map(this::toValue).toList();
	}
	Integer toValue(Document document) {
		return document.getInteger("value");
	}
}
