namespace QuizMauiApp;

public partial class QuizPage : ContentPage
{
    string correctAnswer = "Paris";
    public QuizPage()
    {
        InitializeComponent();
        QuestionLabel.Text = "Quelle est la capitale de la France ?";
    }
    private async void OnAnswerClicked(object sender, EventArgs e)
    {
        if (sender is not Button button)
            return;
        if (button.Text == correctAnswer)
        {
            await DisplayAlertAsync("Résultat", "Bonne réponse !", "OK");
        }
        else
        {
            await DisplayAlertAsync("Résultat", "Mauvaise réponse", "OK");
        }
    }
}