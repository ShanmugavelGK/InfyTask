package com.gkshanmugavel.newapp;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.annotation.IdRes;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import com.gkshanmugavel.newapp.IdlingResource.CustomIdlingResource;
import com.gkshanmugavel.newapp.model.TitleModel;
import com.gkshanmugavel.newapp.view.HomeActivity;
import com.gkshanmugavel.newapp.view.RowAdapter;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.core.AllOf.allOf;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {

    @Rule
    public ActivityTestRule<HomeActivity> activityRule = new ActivityTestRule<>(HomeActivity.class);

    private static TitleModel mockedTitleModel;
    int COUNT = 0;


    @BeforeClass
    public static void setUp() throws InterruptedException {


        Utils.App.Open("com.gkshanmugavel.newapp");

        Thread.sleep(10000);

        //Create mock object of TitleModel
        mockedTitleModel = mock(TitleModel.class);
    }

    @Before
    public void init() {
        IdlingRegistry.getInstance().register(CustomIdlingResource.getIdlingResource());
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(CustomIdlingResource.getIdlingResource());
    }


    @Test
    public void testMockData() {
        List mockList = Mockito.mock(ArrayList.class);
        mockList.add(mockedTitleModel);
        Mockito.verify(mockList).add(mockedTitleModel);
        assertEquals(0, mockList.size());
    }


    /**
     * check the title for initial
     */
    @Test
    public void checkTitle() {
        onView(withText(activityRule.getActivity().getString(R.string.click_list)))
                .check(matches(withText(activityRule.getActivity().getString(R.string.click_list))));
    }

    /**
     * check the title for initial with rotation
     */

    @Test
    public void checkTitleOnRotation() {
        onView(withText(activityRule.getActivity().getString(R.string.click_list)))
                .check(matches(withText(activityRule.getActivity().getString(R.string.click_list))));
        rotateScreen();
        onView(withText(activityRule.getActivity().getString(R.string.click_list)))
                .check(matches(withText(activityRule.getActivity().getString(R.string.click_list))));
    }


    @Test
    public void scrollToLastPosition() {
        onView(withId(R.id.rv_items))
                .perform(RecyclerViewActions.scrollToPosition(activityRule.getActivity().adapter.getItemCount() - 1));
    }


    @Test
    public void scrollTo2ndPositionClick() {
        onView(withId(R.id.rv_items)).perform(smoothScrollTo(2)).perform(click());
        TitleModel mock = activityRule.getActivity().adapter.getItemAt(1);
        onView(withId(R.id.toolbar_title))
                .check(matches(withText(mock.getTitle())));
    }


    @Test
    public void scrollTo2ndPositionClickRotation() {
        onView(withId(R.id.rv_items)).perform(smoothScrollTo(2)).perform(click());
        TitleModel mock = activityRule.getActivity().adapter.getItemAt(1);
        onView(withId(R.id.toolbar_title))
                .check(matches(withText(mock.getTitle())));

        rotateScreen();

        onView(withId(R.id.toolbar_title))
                .check(matches(withText(mock.getTitle())));
    }

    @Test
    public void scrollTo5thPositionClick() {
        onView(withId(R.id.rv_items)).perform(smoothScrollTo(5)).perform(click());
        TitleModel mock = activityRule.getActivity().adapter.getItemAt(4);
        Utils.TimeWait.delay(2);
        onView(withId(R.id.toolbar_title))
                .check(matches(withText(mock.getTitle())));
    }

    @Test
    public void scrollTo5thPositionClickRotation() {
        onView(withId(R.id.rv_items)).perform(smoothScrollTo(5)).perform(click());
        TitleModel mock = activityRule.getActivity().adapter.getItemAt(4);
        onView(withId(R.id.toolbar_title))
                .check(matches(withText(mock.getTitle())));

        rotateScreen();
        onView(withId(R.id.toolbar_title))
                .check(matches(withText(mock.getTitle())));
    }

    @Test
    public void scrollToLastPositionClick() {

        int count = getCountFromRecyclerView(R.id.rv_items);


        //onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition(3));


        onView(withId(R.id.rv_items)).perform(RecyclerViewActions.scrollToPosition(4)).perform(click());

        TitleModel mock = activityRule.getActivity().adapter.getItemAt(2);

        onView(withId(R.id.toolbar_title))
                .check(matches(withText(mock.getTitle())));

    }

    @Test
    public void scrollToLastPositionRotationClick() {
        int position = activityRule.getActivity().adapter.getItemCount();
        onView(withId(R.id.rv_items)).perform(smoothScrollTo(position)).perform(click());
        TitleModel mock = activityRule.getActivity().adapter.getItemAt(position);
        onView(withId(R.id.toolbar_title))
                .check(matches(withText(mock.getTitle())));

        rotateScreen();
        onView(withId(R.id.toolbar_title))
                .check(matches(withText(mock.getTitle())));
    }

    @Test
    public final void shouldContainCertainListItem() {
        List<TitleModel> asList = Arrays.asList(new TitleModel("title", "description", "image"));
        final List<TitleModel> mockedList = mock(List.class);
        mockedList.addAll(asList);

    }


    private void rotateScreen() {
        Context context = InstrumentationRegistry.getTargetContext();
        int orientation = context.getResources().getConfiguration().orientation;

        Activity activity = activityRule.getActivity();
        activity.setRequestedOrientation(
                (orientation == Configuration.ORIENTATION_PORTRAIT) ?
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public static Matcher<RecyclerView.ViewHolder> withHolderTimeView(final String text) {
        return new BoundedMatcher<RecyclerView.ViewHolder, RowAdapter.MyViewHolder>(RowAdapter.MyViewHolder.class) {

            @Override
            protected boolean matchesSafely(RowAdapter.MyViewHolder item) {
                TextView timeViewText = (TextView) item.itemView.findViewById(R.id.tv_title);
                if (timeViewText == null) {
                    return false;
                }
                return timeViewText.getText().toString().contains(text);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("No ViewHolder found with text: " + text);
            }
        };
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    public static ScrollToPositionViewAction smoothScrollTo(int position) {
        return new ScrollToPositionViewAction(position);
    }


    public int getCountFromRecyclerView(@IdRes int RecyclerViewId) {

        Matcher matcher = new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                COUNT = ((RecyclerView) item).getAdapter().getItemCount();
                return true;
            }

            @Override
            public void describeTo(Description description) {
            }
        };
        onView(allOf(withId(RecyclerViewId), isDisplayed())).check(matches(matcher));
        int result = COUNT;
        COUNT = 0;
        return result;
    }


    /***************************************************************************************
     * Method Name : getRecyclerViewCount
     * Arguments :  matcher -  view which you want to match
     * Returns: count - Item count of the recyclerview
     * Method Description : Use this method to get the item count of a recyclerview
     ***********************************x*****************************************************/
    public static int getRecyclerViewCount(Matcher matcher) {
        final int[] num = new int[1];
        onView(allOf(matcher, isEnabled())).check(matches(new TypeSafeMatcher<View>() {
            RecyclerView recyclerView = null;

            @Override
            public boolean matchesSafely(View view) {
                recyclerView = (RecyclerView) view;
                RecyclerView.Adapter adapter = recyclerView.getAdapter();
                Log.d("Booking Recycler Cnt", adapter.getItemCount() + "");
                num[0] = adapter.getItemCount();
                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));
        return num[0];

    }
}