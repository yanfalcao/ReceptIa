import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.receptia.feature.login.LoginViewModel

class LoginViewModelPreviewParameterProvider : PreviewParameterProvider<LoginViewModel> {
    override val values: Sequence<LoginViewModel> = sequenceOf(LoginViewModel())
}
