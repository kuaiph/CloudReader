package com.example.jingbin.cloudreader.viewmodel.movie;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.example.jingbin.cloudreader.bean.ComingFilmBean;
import com.example.jingbin.cloudreader.bean.MtimeFilmeBean;
import com.example.jingbin.cloudreader.bean.book.BookBean;
import com.example.jingbin.cloudreader.http.HttpClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * @author jingbin
 * @data 2019/05/14
 */

public class FilmViewModel extends AndroidViewModel {

    // 开始请求的角标
    private int mStart = 0;
    // 一次请求的数量
    private int mCount = 18;

    public final ObservableField<String> bookType = new ObservableField<>();

    public FilmViewModel(@NonNull Application application) {
        super(application);
    }

    public void setStart(int mStart) {
        this.mStart = mStart;
    }

    public int getStart() {
        return mStart;
    }

    @SuppressLint("CheckResult")
    public MutableLiveData<BookBean> getBook() {
        final MutableLiveData<BookBean> data = new MutableLiveData<>();
        HttpClient.Builder.getDouBanService().getBook(bookType.get(), mStart, mCount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BookBean>() {
                    @Override
                    public void accept(BookBean bookBean) throws Exception {
                        data.setValue(bookBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        data.setValue(null);
                    }
                });
        return data;
    }

    public void handleNextStart() {
        mStart += mCount;
    }

    @SuppressLint("CheckResult")
    public MutableLiveData<MtimeFilmeBean> getHotFilm() {
        final MutableLiveData<MtimeFilmeBean> data = new MutableLiveData<>();
        HttpClient.Builder.getMtimeServer().getHotFilm()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MtimeFilmeBean>() {
                    @Override
                    public void accept(MtimeFilmeBean bookBean) throws Exception {
                        data.setValue(bookBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        data.setValue(null);
                    }
                });
        return data;
    }

    @SuppressLint("CheckResult")
    public MutableLiveData<ComingFilmBean> getComingFilm() {
        final MutableLiveData<ComingFilmBean> data = new MutableLiveData<>();
        HttpClient.Builder.getMtimeServer().getComingFilm()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ComingFilmBean>() {
                    @Override
                    public void accept(ComingFilmBean bookBean) throws Exception {
                        data.setValue(bookBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        data.setValue(null);
                    }
                });
        return data;
    }
}
