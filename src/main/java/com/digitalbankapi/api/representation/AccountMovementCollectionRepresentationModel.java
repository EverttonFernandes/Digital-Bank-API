package com.digitalbankapi.api.representation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.Link;

import java.util.List;
import java.util.Map;

@Schema(name = "AccountMovementCollectionRepresentationModel", description = "Representacao HAL da colecao de movimentacoes da conta.")
public class AccountMovementCollectionRepresentationModel {

    @JsonProperty("_embedded")
    @Schema(description = "Recursos embutidos da colecao.")
    private final EmbeddedMovements _embedded;

    @JsonProperty("_links")
    @Schema(description = "Links de navegacao da colecao.")
    private final Map<String, Link> _links;

    public AccountMovementCollectionRepresentationModel(EmbeddedMovements embedded, Map<String, Link> links) {
        this._embedded = embedded;
        this._links = links;
    }

    @Schema(hidden = true)
    @JsonIgnore
    public EmbeddedMovements getEmbedded() {
        return _embedded;
    }

    @Schema(hidden = true)
    @JsonIgnore
    public Map<String, Link> getLinks() {
        return _links;
    }

    @Schema(name = "EmbeddedMovements", description = "Envelope HAL para a colecao de movimentacoes.")
    public static class EmbeddedMovements {

        @ArraySchema(schema = @Schema(implementation = AccountMovementRepresentationModel.class))
        private final List<AccountMovementRepresentationModel> movements;

        public EmbeddedMovements(List<AccountMovementRepresentationModel> movements) {
            this.movements = movements;
        }

        public List<AccountMovementRepresentationModel> getMovements() {
            return movements;
        }
    }
}
