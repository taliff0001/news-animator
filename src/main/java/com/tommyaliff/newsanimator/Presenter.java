package com.tommyaliff.livenews;

public class Presenter {

    private final View view;
    private final Model model;

        public Presenter(View view, Model model) {
            this.model = model;
            this.view = view;
            attach();
        }

        public void attach() {

            view.getButton.setOnAction(e -> {
                view.headlines.setText(model.getNewsTitle());
                view.description.setText(model.getNewsDescription());
                view.imageView.setImage(model.getNewsImage());
                model.index++;
            });

            view.fakeButton.setOnAction(e -> {
                view.description.setText("");
                view.imageView.setImage(model.getFakeNews());
                view.headlines.setText(model.getFakeTitle());
            });
        }

    }


