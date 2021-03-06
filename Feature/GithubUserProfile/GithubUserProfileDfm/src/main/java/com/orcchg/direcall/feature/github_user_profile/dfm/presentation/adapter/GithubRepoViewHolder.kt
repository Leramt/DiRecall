package com.orcchg.direcall.feature.github_user_profile.dfm.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.orcchg.direcall.feature.github_user_profile.dfm.databinding.RvGithubUserRepoListItemInProfileBinding
import com.orcchg.direcall.feature.githubrepo.api.model.GithubRepo

class GithubRepoViewHolder(
    private val binding: RvGithubUserRepoListItemInProfileBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: GithubRepo) {
        binding.tvTitle.text = model.name
    }
}
