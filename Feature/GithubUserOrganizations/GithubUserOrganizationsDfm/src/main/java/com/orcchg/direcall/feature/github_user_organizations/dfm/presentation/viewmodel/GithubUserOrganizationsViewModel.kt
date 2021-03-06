package com.orcchg.direcall.feature.github_user_organizations.dfm.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orcchg.direcall.core.analytics.api.Analytics
import com.orcchg.direcall.core.ui.AutoDisposeViewModel
import com.orcchg.direcall.feature.github_user_organizations.api.di.OrgLogin
import com.orcchg.direcall.feature.github_user_organizations.api.interactor.GithubOrganizationInteractor
import com.orcchg.direcall.feature.github_user_organizations.api.model.GithubOrganization
import com.uber.autodispose.autoDispose
import timber.log.Timber
import javax.inject.Inject

class GithubUserOrganizationsViewModel @Inject constructor(
    @OrgLogin private val login: String,
    private val analytics: Analytics,
    private val interactor: GithubOrganizationInteractor
) : AutoDisposeViewModel() {

    val organizations: LiveData<List<GithubOrganization>> by lazy(LazyThreadSafetyMode.NONE) {
        val liveData = MutableLiveData<List<GithubOrganization>>()
        interactor.organizations(login)
            .doOnSubscribe { analytics.sendEvent("get_organizations", "Get list of organizations for github user $login") }
            .autoDispose(this)
            .subscribe({ liveData.value = it }, Timber::e)
        liveData
    }
}
