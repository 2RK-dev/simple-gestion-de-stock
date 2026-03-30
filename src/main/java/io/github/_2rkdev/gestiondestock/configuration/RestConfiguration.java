package io.github._2rkdev.gestiondestock.configuration;

import io.github._2rkdev.gestiondestock.model.ApprovisionnementProjection;
import io.github._2rkdev.gestiondestock.model.Fournisseur;
import io.github._2rkdev.gestiondestock.model.Produit;
import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RestConfiguration implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(@NonNull RepositoryRestConfiguration config, @NonNull CorsRegistry cors) {
        config.getExposureConfiguration()
                .withItemExposure((metadata, httpMethods) ->
                        httpMethods.disable(HttpMethod.PATCH))
                .withAssociationExposure((metadata, httpMethods) ->
                        httpMethods.disable(HttpMethod.DELETE, HttpMethod.PATCH, HttpMethod.POST, HttpMethod.PUT));

        for (Class<?> domainType : new Class[]{Fournisseur.class, Produit.class}) {
            config.getExposureConfiguration()
                    .forDomainType(domainType)
                    .withItemExposure((metadata, httpMethods) ->
                            httpMethods.disable(HttpMethod.PUT));
        }

        config.getProjectionConfiguration()
                .addProjection(ApprovisionnementProjection.class);
    }
}
