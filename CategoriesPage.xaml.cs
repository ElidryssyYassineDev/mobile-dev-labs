

namespace QuizMauiApp;

public partial class CategoriesPage : ContentPage
{
    public CategoriesPage()
    {
        InitializeComponent();
    }

    // ─── Step 11: Read button text → pass as category ────
    async void OnCategoryClicked(object sender, EventArgs e)
    {
        if (sender is not Button button) return;

        string selectedCategory = button.Text;

        // Navigate to QuizPage and pass the category as a query parameter
        await Shell.Current.GoToAsync(
            $"{nameof(QuizPage)}?category={Uri.EscapeDataString(selectedCategory)}"
        );
    }
}