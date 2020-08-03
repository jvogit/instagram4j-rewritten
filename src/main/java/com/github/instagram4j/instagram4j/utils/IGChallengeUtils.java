package com.github.instagram4j.instagram4j.utils;

import java.io.IOException;
import java.util.concurrent.Callable;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;
import com.github.instagram4j.instagram4j.requests.challenge.ChallengeResetRequest;
import com.github.instagram4j.instagram4j.requests.challenge.ChallengeSelectVerifyMethodRequest;
import com.github.instagram4j.instagram4j.requests.challenge.ChallengeSendCodeRequest;
import com.github.instagram4j.instagram4j.requests.challenge.ChallengeStateGetRequest;
import com.github.instagram4j.instagram4j.responses.accounts.LoginResponse;
import com.github.instagram4j.instagram4j.responses.challenge.Challenge;
import com.github.instagram4j.instagram4j.responses.challenge.ChallengeStateResponse;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IGChallengeUtils {

    public static ChallengeStateResponse requestState(IGClient client, Challenge challenge)
            throws IOException {
        return client.sendRequest(
                new ChallengeStateGetRequest(challenge.getApi_path(), client.getGuid(), client.getDeviceId()));
    }

    public static ChallengeStateResponse selectVerifyMethod(IGClient client, Challenge challenge, String method,
            boolean resend) throws IOException {
        return client.sendRequest(new ChallengeSelectVerifyMethodRequest(challenge.getApi_path(), method, resend));
    }

    public static LoginResponse selectVerifyMethodDelta(IGClient client, Challenge challenge, String method,
            boolean resend) throws IOException {
        return client.sendRequest(new ChallengeSelectVerifyMethodRequest(challenge.getApi_path(), method, resend),
                LoginResponse.class);
    }

    public static LoginResponse sendSecurityCode(IGClient client, Challenge challenge, String code)
            throws IOException {
        return client.sendRequest(new ChallengeSendCodeRequest(challenge.getApi_path(), code));
    }

    public static ChallengeStateResponse resetChallenge(IGClient client, Challenge challenge)
            throws IOException {
        return client.sendRequest(new ChallengeResetRequest(challenge.getApi_path()));
    }

    @SneakyThrows
    public static LoginResponse resolve(@NonNull IGClient client, @NonNull LoginResponse response,
            @NonNull Callable<String> inputCode, int retries) throws IOException {
        Challenge challenge = response.getChallenge();
        ChallengeStateResponse stateResponse = requestState(client, challenge);
        String name = stateResponse.getStep_name();

        if (name.equalsIgnoreCase("select_verify_method")) {
            // verify by phone or email
            selectVerifyMethod(client, challenge, stateResponse.getStep_data().getChoice(), false);
            log.info("select_verify_method option security code sent to "
                    + (stateResponse.getStep_data().getChoice().equals("1") ? "email" : "phone"));
            response = sendSecurityCode(client, challenge, inputCode.call());
            while (!response.getStatus().equalsIgnoreCase("ok") && --retries > 0) {
                log.info("{} : {}", response.getError_type(), response.getMessage());
                response = sendSecurityCode(client, challenge, inputCode.call());
            }
        } else if (name.equalsIgnoreCase("delta_login_review")) {
            // 'This was me' option
            log.info("delta_login_review option sent choice 0");
            response = selectVerifyMethodDelta(client, challenge, "0", false);
        } else {
            // Unknown step_name
        }
        
        client.setLoggedInState(response);

        return response;
        
    }

    public static LoginResponse resolve(@NonNull IGClient client, @NonNull LoginResponse response,
            @NonNull Callable<String> inputCode) throws IOException {
        return resolve(client, response, inputCode, 3);
    }

    @SneakyThrows
    public static LoginResponse resolveTwoFactor(@NonNull IGClient client, @NonNull LoginResponse response,
            @NonNull Callable<String> inputCode) {
        return resolveTwoFactor(client, response, inputCode, 3);
    }

    @SneakyThrows
    public static LoginResponse resolveTwoFactor(@NonNull IGClient client, @NonNull LoginResponse response,
            @NonNull Callable<String> inputCode, int retries) {
        String identifier = response.getTwo_factor_info().getTwo_factor_identifier();
        do {
            String code = inputCode.call();

            try {
                response = client.sendLoginRequest(code, identifier);
            } catch (IGLoginException e) {
                response = e.getLoginResponse();
                log.info(e.getMessage());
            }
        } while (!response.getStatus().equals("ok") && --retries > 0);

        return response;
    }
}