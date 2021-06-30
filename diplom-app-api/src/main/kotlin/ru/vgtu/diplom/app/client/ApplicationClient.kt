package ru.vgtu.diplom.app.client

import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import ru.vgtu.diplom.app.model.*

class ApplicationClient(
    private val webClient: WebClient
) {

    fun getApplicationInfo(clientId: String) =
        webClient.get()
            .uri("/app/info?clientId=${clientId}")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(GetApplicationInfo::class.java)


    fun createApplication(postApplication: PostApplication) =
        webClient.post()
            .uri("/app")
            .body(Mono.just(postApplication), PostApplication::class.java)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(PostApplicationInfo::class.java)

    fun updateApplication(application: Application) =
        webClient.put()
            .uri("/app")
            .body(Mono.just(application), Application::class.java)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Unit::class.java)

    fun getApplicationByAppId(appId: String) =
        webClient.get()
            .uri("/app/${appId}")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Application::class.java)

    fun getDecision(appId: String) =
        webClient.get()
            .uri("/app/${appId}/decision")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Decision::class.java)

    fun updateDecision(appId: String, decision: Decision) =
        webClient.put()
            .uri("/app/${appId}/decision")
            .body(Mono.just(decision), Decision::class.java)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Unit::class.java)

    fun updateStatus(appId: String, status: Status) =
        webClient.put()
            .uri("/app/${appId}/status")
            .body(Mono.just(status), Status::class.java)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Unit::class.java)

    fun getCondition(clientId: String) =
        webClient.get()
            .uri("/condition/${clientId}")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Condition::class.java)

    fun writeCondition(clientId: String, condition: Condition) =
        webClient.put()
            .uri("/condition/${clientId}")
            .body(Mono.just(condition), Condition::class.java)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Unit::class.java)

    fun getProfile(appId: String) =
        webClient.get()
            .uri("/profile/${appId}")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Profile::class.java)

    fun updateProfile(appId: String, profile: Profile) =
        webClient.put()
            .uri("/profile/${appId}")
            .body(Mono.just(profile), Profile::class.java)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Unit::class.java)

}