package com.orcchg.direcall.feature.github_user_organizations.impl.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.orcchg.direcall.androidutil.argument
import com.orcchg.direcall.androidutil.observe
import com.orcchg.direcall.androidutil.viewBindings
import com.orcchg.direcall.core.di.FeatureContainer
import com.orcchg.direcall.core.di.getFeature
import com.orcchg.direcall.feature.github_user_organizations.impl.R
import com.orcchg.direcall.feature.github_user_organizations.impl.databinding.FragmentGithubUserOrganizationListBinding
import com.orcchg.direcall.feature.github_user_organizations.impl.di.DaggerGithubOrganizationFeatureComponent
import com.orcchg.direcall.feature.github_user_organizations.impl.di.DaggerGithubOrganizationFeatureDependenciesComponent
import com.orcchg.direcall.feature.github_user_organizations.impl.presentation.adapter.GithubOrganizationsAdapter
import com.orcchg.direcall.feature.github_user_organizations.impl.presentation.viewmodel.GithubUserOrganizationsViewModel
import com.orcchg.direcall.feature.github_user_organizations.impl.presentation.viewmodel.GithubUserOrganizationsViewModelFactory
import javax.inject.Inject

class GithubUserOrganizationsFragment : Fragment(R.layout.fragment_github_user_organization_list) {

    @Inject lateinit var factory: GithubUserOrganizationsViewModelFactory

    private val adapter = GithubOrganizationsAdapter()
    private val binding by viewBindings(FragmentGithubUserOrganizationListBinding::bind)
    private val login by argument<String>("login")
    private val viewModel by viewModels<GithubUserOrganizationsViewModel> { factory }

    override fun onAttach(context: Context) {
        val featureContainer = (requireActivity().application as FeatureContainer)

        DaggerGithubOrganizationFeatureDependenciesComponent.factory()
            .create(
                analyticsCoreLibApi = featureContainer.getFeature(),
                netCoreLibApi = featureContainer.getFeature(),
                schedulerCoreLibApi = featureContainer.getFeature()
            )
            .let { dependencies ->
                DaggerGithubOrganizationFeatureComponent.factory()
                    .create(
                        login = login,
                        dependencies = dependencies
                    )
            }
            .inject(this)

        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvOrganizations.adapter = adapter

        observe(viewModel.organizations, adapter::update)
    }
}
