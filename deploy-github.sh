#!/bin/bash

# Build the application for GitHub Pages
echo "🔨 Building Water Tracker for GitHub Pages..."
cd frontend
npm run build:github

# Check if build was successful
if [ $? -eq 0 ]; then
    echo "✅ Build completed successfully!"
    echo "📁 Files are ready in frontend/dist/frontend/"
    echo ""
    echo "📋 Next steps for GitHub Pages deployment:"
    echo "1. Create a new repository on GitHub called 'water-tracker'"
    echo "2. Copy the contents of frontend/dist/frontend/ to the root of your repository"
    echo "3. Enable GitHub Pages in repository settings"
    echo "4. Your app will be available at: https://yourusername.github.io/water-tracker/"
    echo ""
    echo "🔗 Or use GitHub Actions for automatic deployment!"
else
    echo "❌ Build failed. Please check the errors above."
    exit 1
fi
