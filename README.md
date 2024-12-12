# News Animator
**News Animator** is a JavaFX application that retrieves entertainment news headlines from the [NewsAPI](https://newsapi.org/). Users can choose to display either:
- An AI-generated image of the headline created using Stability AI's API
- The standard image associated with the headline.
The application follows the Model-View-Presenter (MVP) architectural pattern.

<em> *Please note that not all new stories contain images</em>

## Features
- **Entertainment News Retrieval**: Fetches the latest entertainment news headlines.
- **Display Options**:
  - AI-generated interpretations of headlines (using Stability AI's Stable Diffusion)
  - Standard image associated with the headline
- **Architecture**: Implements the Model-View-Presenter (MVP) pattern.

## Technology Stack
- **Programming Language**: Java
- **Framework**: JavaFX for the user interface
- **APIs**:
  - [NewsAPI](https://newsapi.org/) for news headline retrieval
  - [Stability AI](https://stability.ai/) for generating AI images

## Setup
### API Keys
The application requires two API keys to function:
1. A NewsAPI key from https://newsapi.org/
2. A Stability AI API key from https://stability.ai/

You can set these keys using environment variables:
```bash
STABILITY_API_KEY=your_stability_api_key
NEWS_API_KEY=your_news_api_key
```

Alternatively, create a `.env` file in the project root with the following content:
```
STABILITY_API_KEY=your_stability_api_key
NEWS_API_KEY=your_news_api_key
```

Note: The `.env` file is ignored by git to keep your API keys secure.
