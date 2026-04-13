

namespace QuizMauiApp;

[QueryProperty(nameof(Score), "score")]
[QueryProperty(nameof(TotalQuestions), "totalQuestions")]
public partial class ResultPage : ContentPage
{
    // ─── Private backing fields ───────────────────────────
    private int _score;
    private int _totalQuestions;

    // ─── Shell will inject these via query parameters ─────
    public int Score
    {
        get => _score;
        set
        {
            _score = value;
            UpdateUI();
        }
    }

    public int TotalQuestions
    {
        get => _totalQuestions;
        set
        {
            _totalQuestions = value;
            UpdateUI();
        }
    }

    public ResultPage()
    {
        InitializeComponent();
    }

    // ─── Step 10: Update labels once both values are set ─
    void UpdateUI()
    {
        // Only update when both values are received
        if (ScoreLabel == null) return;

        ScoreLabel.Text = $"Score : {_score} / {_totalQuestions}";

        // Bonus: motivational message
        double percent = _totalQuestions > 0 ? (double)_score / _totalQuestions : 0;

        MessageLabel.Text = percent switch
        {
            1.0 => "🎉 Parfait ! Tu as tout bon !",
            >= 0.7 => "👍 Très bien ! Continue comme ça !",
            >= 0.5 => "😊 Pas mal, tu peux faire mieux !",
            _ => "📚 Continue de t'entraîner !"
        };
    }

    // ─── Step 10: Return to home page ────────────────────
    async void OnGoHomeClicked(object sender, EventArgs e)
    {
        // Navigate back to the root (home) page
        await Shell.Current.GoToAsync("//MainPage");
    }
}