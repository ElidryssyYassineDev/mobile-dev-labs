namespace QuizMauiApp;

public partial class CategoriesPage : ContentPage
{
	public CategoriesPage()
	{
		InitializeComponent();
	}
    private async void OnCategoryClicked(object sender, EventArgs e)
    {
        await Shell.Current.GoToAsync(nameof(QuizPage));
    }
}