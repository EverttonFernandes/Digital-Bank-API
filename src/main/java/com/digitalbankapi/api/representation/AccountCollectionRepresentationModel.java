package com.digitalbankapi.api.representation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.Link;

import java.util.List;
import java.util.Map;

@Schema(name = "AccountCollectionRepresentationModel", description = "Representacao HAL da colecao de contas.")
public class AccountCollectionRepresentationModel {

    @JsonProperty("_embedded")
    @Schema(description = "Recursos embutidos da colecao.")
    private final EmbeddedAccounts _embedded;

    @JsonProperty("_links")
    @Schema(description = "Links de navegacao da colecao.")
    private final Map<String, Link> _links;

    public AccountCollectionRepresentationModel(EmbeddedAccounts embedded, Map<String, Link> links) {
        this._embedded = embedded;
        this._links = links;
    }

    @Schema(hidden = true)
    @JsonIgnore
    public EmbeddedAccounts getEmbedded() {
        return _embedded;
    }

    @Schema(hidden = true)
    @JsonIgnore
    public Map<String, Link> getLinks() {
        return _links;
    }

    @Schema(name = "EmbeddedAccounts", description = "Envelope HAL para a colecao de contas.")
    public static class EmbeddedAccounts {

        @ArraySchema(schema = @Schema(implementation = AccountRepresentationModel.class))
        private final List<AccountRepresentationModel> accounts;

        public EmbeddedAccounts(List<AccountRepresentationModel> accounts) {
            this.accounts = accounts;
        }

        public List<AccountRepresentationModel> getAccounts() {
            return accounts;
        }
    }
}
