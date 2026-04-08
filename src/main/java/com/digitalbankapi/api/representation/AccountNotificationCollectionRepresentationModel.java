package com.digitalbankapi.api.representation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.Link;

import java.util.List;
import java.util.Map;

@Schema(name = "AccountNotificationCollectionRepresentationModel", description = "Representacao HAL da colecao de notificacoes da conta.")
public class AccountNotificationCollectionRepresentationModel {

    @JsonProperty("_embedded")
    @Schema(description = "Recursos embutidos da colecao.")
    private final EmbeddedNotifications _embedded;

    @JsonProperty("_links")
    @Schema(description = "Links de navegacao da colecao.")
    private final Map<String, Link> _links;

    public AccountNotificationCollectionRepresentationModel(EmbeddedNotifications embedded, Map<String, Link> links) {
        this._embedded = embedded;
        this._links = links;
    }

    @Schema(hidden = true)
    @JsonIgnore
    public EmbeddedNotifications getEmbedded() {
        return _embedded;
    }

    @Schema(hidden = true)
    @JsonIgnore
    public Map<String, Link> getLinks() {
        return _links;
    }

    @Schema(name = "EmbeddedNotifications", description = "Envelope HAL para a colecao de notificacoes.")
    public static class EmbeddedNotifications {

        @ArraySchema(schema = @Schema(implementation = AccountNotificationRepresentationModel.class))
        private final List<AccountNotificationRepresentationModel> notifications;

        public EmbeddedNotifications(List<AccountNotificationRepresentationModel> notifications) {
            this.notifications = notifications;
        }

        public List<AccountNotificationRepresentationModel> getNotifications() {
            return notifications;
        }
    }
}
