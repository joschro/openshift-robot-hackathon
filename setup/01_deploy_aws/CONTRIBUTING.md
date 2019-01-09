# Contributing

> **WARNING:** This guide is a work in progress and will continue to evolve over
> time. If you have content to contribute, please refer to this document
> each time as things may have changed since the last time you contributed.
>
> This warning will be removed once we have settled on a reasonable set of
> guidelines for contributions.

Thank you for contributing to OpenShift AWS Setup. This document explains how the
repository is organized, and how to submit contributions.

### 1 Fork the openshift-aws-setup repo

Forking openshift-aws-setup is a simple two-step process.

1.  On GitHub, navigate to the <https://github.com/gnunn1/openshift-aws-setup> repo.
2.  In the top-right corner of the page, click **Fork**.

That's it! Now, you have a [fork][git-fork] of the original gnunn1/openshift-aws-setup repo.

### 2 Create a local clone of your fork

Right now, you have a fork of the openshift-aws-setup repo, but you don't have the files in that repo on your computer. Let's create a [clone][git-clone] of your fork locally on your computer.

```sh
git clone git@github.com:your-username/openshift-aws-setup
cd openshift-aws-setup

# Configure git to sync your fork with the original repo
git remote add upstream https://github.com/gnunn1/openshift-aws-setup

# Never push to upstream repo
git remote set-url --push upstream no_push
```

### 3 Verify your [remotes][git-remotes]

To verify the new upstream repo you've specified for your fork, type
`git remote -v`. You should see the URL for your fork as `origin`, and the URL for the original repo as `upstream`.

```sh
origin  git@github.com:your-username/openshift-aws-setup (fetch)
origin  git@github.com:your-username/openshift-aws-setup (push)
upstream        https://github.com/gnunn1/openshift-aws-setup (fetch)
upstream        no_push (push)
```

### 4 Modify your `develop`

Get your local `develop` [branch][git-branch], up to date:

```sh
git checkout develop
git fetch upstream
git merge upstream/develop
```

Then build your local `develop` branch, make changes, etc.

### 5 Keep your local clone in sync

```sh
git fetch upstream
git merge upstream/master
git merge upstream/develop
```

### 6 [Commit][git-commit] your `develop`

```sh
git commit
```

Likely you'll go back and edit, build, test, etc.

### 7 [Push][git-push] your `develop`

When ready to review (or just to establish an offsite backup of your work),
push your branch to your fork on `github.com`:

```sh
git push
```

### 8 Submit a [pull request][git-pull-request]

1.  Visit your fork at <https://github.com/your-username/openshift-aws-setup>
2.  Click the `Compare & Pull Request` button next to your `develop` branch.

At this point you're waiting on us. We may suggest some changes or improvements or alternatives. We'll do our best to review and at least comment within 3 business days (often much sooner).

_If you have upstream write access_, please refrain from using the GitHub UI for creating PRs, because GitHub will create the PR branch inside the main repo rather than inside your fork.

[git-fork]: https://help.github.com/articles/fork-a-repo/
[git-clone]: https://git-scm.com/docs/git-clone
[git-remotes]: https://git-scm.com/book/en/v2/Git-Basics-Working-with-Remotes
[git-branch]: https://git-scm.com/docs/git-branch
[git-commit]: https://git-scm.com/docs/git-commit
[git-push]: https://git-scm.com/docs/git-push
[git-pull-request]: https://github.com/gnunn1/openshift-aws-setup/compare/
