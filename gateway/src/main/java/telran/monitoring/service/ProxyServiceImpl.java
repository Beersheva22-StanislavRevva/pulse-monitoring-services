package telran.monitoring.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import telran.spring.exceptions.NotFoundException;

@Service
@Slf4j
public class ProxyServiceImpl implements ProxyService {
@Value("#{${app.proxied.urls}}")
	Map<String,String> urlsMap;


	@Override
	public ResponseEntity<byte[]> proxyRouting(ProxyExchange<byte[]> proxy, HttpServletRequest request) {
		String routedURI = getRoutedURI(request);
		log.trace("routed URI:{}", routedURI);
		return proxy.uri(routedURI).get();
	}

	private String getRoutedURI(HttpServletRequest request) {
		String recievedURI = request.getRequestURI(); //example, /pulse/values/max/9
		String firstURN = recievedURI.split("/+")[1]; //pulse
		if (!urlsMap.containsKey(firstURN)) {
			throw new NotFoundException(firstURN + " not found" );
			}
		String routedURL = urlsMap.get(firstURN); //http://pulse-values-back-office:9090
		log.trace("routedURL {}", routedURL);
		String queryString = request.getQueryString(); //from=2023-10-26T14:00&to=2023-10-26T16:00
		if(queryString == null) {
			queryString = "";
		}
		
		return String.format("%s%s?%s", routedURL, recievedURI, queryString); //http://pulse-values-back-office:9090/pulse/values/max/9?from=2023-10-26T14:00&to=2023-10-26T16:00 
	}
	@PostConstruct
	void mapLogging() {
		log.debug("mapping of routed URLs us {}", urlsMap);
	}

}
