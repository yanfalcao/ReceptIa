package com.nexusfalcao.avatar

import com.nexusfalcao.avatar.state.ImageUiState
import com.nexusfalcao.data.repository.UserRepository
import com.nexusfalcao.model.User
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.never
import org.mockito.Mockito.`when`
import java.util.UUID

class AvatarViewModelTest {
    private val imageId = 1

    private lateinit var avatarViewModel: AvatarViewModel
    private val userRepository: UserRepository = Mockito.mock(UserRepository::class.java)

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        avatarViewModel = AvatarViewModel(userRepository)
    }

    @Test
    fun `check if UiState starts unselected`() {
        Assert.assertEquals(ImageUiState.Unselected, avatarViewModel.imageUiState.value)
    }

    @Test
    fun `select image if the same is unselected sets selectedState`() {
        avatarViewModel.selectImage(imageId)

        Assert.assertEquals(ImageUiState.Selected(imageId), avatarViewModel.imageUiState.value)
    }

    @Test
    fun `select image when selected with same id sets UnselectedState`() {
        avatarViewModel.selectImage(imageId)
        avatarViewModel.selectImage(imageId)

        Assert.assertEquals(ImageUiState.Unselected, avatarViewModel.imageUiState.value)
    }

    @Test
    fun `select image when selected with different id sets SelectedState`() {
        avatarViewModel.selectImage(1)
        avatarViewModel.selectImage(2)

        Assert.assertEquals(ImageUiState.Selected(2), avatarViewModel.imageUiState.value)
    }

    @Test
    fun `save image when selected updatesUserPhotoId`() {
        val userId = UUID.randomUUID().toString()
        val user = Mockito.mock(User::class.java)

        `when`(userRepository.findUser()).thenReturn(user)
        `when`(user.id).thenReturn(userId)

        avatarViewModel.selectImage(1)
        avatarViewModel.saveImage()

        Mockito.verify(userRepository).updatePhotoId(userId, imageId)
    }

    @Test
    fun `save image when unselected does not UpdateUserPhotoId`() {
        avatarViewModel.saveImage()

        Mockito.verify(userRepository, never()).updatePhotoId(anyString(), anyInt())
    }
}
