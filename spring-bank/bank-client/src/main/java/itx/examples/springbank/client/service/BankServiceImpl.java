package itx.examples.springbank.client.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import itx.examples.springbank.common.dto.Client;
import itx.examples.springbank.common.dto.ClientId;
import itx.examples.springbank.common.dto.DepositRequest;
import itx.examples.springbank.common.dto.ServiceException;
import itx.examples.springbank.common.dto.TransactionRequest;
import itx.examples.springbank.common.dto.WithdrawRequest;
import itx.examples.springbank.common.service.BankService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BankServiceImpl implements BankService {

    private final static String SERVICE_PREFIX = "/services/bank";

    private final HttpClient httpClient;
    private final ObjectMapper mapper;
    private String baseUri;

    public BankServiceImpl(HttpClient httpClient, String baseUri, ObjectMapper mapper) {
        this.httpClient = httpClient;
        this.baseUri = baseUri + SERVICE_PREFIX;
        this.mapper = mapper;
    }

    @Override
    public void transferFunds(TransactionRequest transactionRequest) throws ServiceException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(transactionRequest)))
                    .uri(URI.create(baseUri + "/transfer"))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new ServiceException();
            }
        } catch (Exception e) {
            throw new ServiceException();
        }
    }

    @Override
    public void deposit(DepositRequest depositRequest) throws ServiceException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .PUT(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(depositRequest)))
                    .uri(URI.create(baseUri + "/deposit"))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new ServiceException();
            }
        } catch (Exception e) {
            throw new ServiceException();
        }
    }

    @Override
    public void withDraw(WithdrawRequest withdrawRequest) throws ServiceException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .PUT(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(withdrawRequest)))
                    .uri(URI.create(baseUri + "/withdraw"))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new ServiceException();
            }
        } catch (Exception e) {
            throw new ServiceException();
        }
    }

    @Override
    public Client getClientInfo(ClientId id) throws ServiceException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(baseUri + "/client/" + id.getId()))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new ServiceException();
            }
            return mapper.readValue(response.body(), Client.class);
        } catch (Exception e) {
            throw new ServiceException();
        }
    }

}
